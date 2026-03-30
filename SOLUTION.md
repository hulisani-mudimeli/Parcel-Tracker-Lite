# SOLUTION.md

## Overview

ParcelTrackerLite is a consumer Android app that allows users to track parcel deliveries 
from multiple carriers. Users can add tracking numbers, view a consolidated list of 
shipments with their current status, and open a details screen to see a full delivery 
timeline.

---

## Architecture

The app follows **Clean Architecture** principles, structured across multiple Gradle modules 
to enforce strict separation of concerns and prevent unintended dependencies between layers.

### Module Structure
```
ParcelTrackerLite/
├── app/                  # UI, ViewModels, Navigation
├── domain/               # Use cases, repository interfaces, domain models
├── common/               # Shared utilities, helpers, UI components
└── data/
    ├── database/         # ObjectBox entities, local data source
    ├── network/          # Mock API service, JSON fixtures, DTOs
    └── repository/       # Repository implementations, DI bindings
```

### Dependency Graph
```
app
 ├── implementation → :domain
 └── runtimeOnly   → :data:repository
                          ├── :domain
                          ├── :data:database
                          └── :data:network
```

### Why This Dependency Structure?

The dependency structure was intentionally designed to enforce architectural boundaries:

- **`:domain`** knows nothing about any implementation detail — no database, no network, 
  no Android framework. It only defines repository interfaces and use cases. This means 
  developers are structurally prevented from leaking implementation details into the domain.

- **`:app`** depends on `:domain` via `implementation` and on `:data:repository` via 
  `runtimeOnly`. This means the app can use domain interfaces and use cases at compile 
  time, but cannot directly reference any class from the data layer. Hilt discovers and 
  wires the repository implementations at runtime without the app needing to know about them.

- This enforces a clean boundary — a developer working in the app layer literally cannot 
  call a database function directly, even if they wanted to. The compiler prevents it.

---

## Key Design Decisions

### 1. Approach: Show Only User-Added Tracking Numbers

**Decision:** The shipments list shows only tracking numbers explicitly added by the user, 
not all items in the fixture data source.

**Reasoning:**
- Showing all items from the data source immediately makes the "add tracking number" flow 
  redundant — why add something already displayed?
- Duplicate prevention only makes sense in a user-curated list context.
- Real-world parcel trackers (AfterShip, 17track) all follow this pattern.
- The fixture data acts as a mock backend lookup — the user enters a tracking number, the 
  app validates it against the fixture, and if found, adds it to their personal list.

**Trade-off:** The 500-item performance requirement becomes less realistic in this model 
since users are unlikely to manually add hundreds of parcels. However, `LazyColumn` with 
proper keying was still implemented as a demonstration of performance-conscious list 
rendering, which is a valid senior-level competency signal.

### 2. Optional Carrier and Title Fields Removed from Add Flow

**Decision:** The add tracking screen only asks for a tracking number, not carrier or title.

**Reasoning:** Since the fixture data already contains carrier and title for every known 
tracking number, asking the user to provide them is redundant and poor UX. The app fetches 
them automatically on a successful lookup. If the tracking number is not found in the 
fixture, an error is surfaced using the provided error shape.

**Trade-off:** In a real-world scenario where the API might return incomplete data, 
allowing user-provided labels would be valuable. This could be added as a future 
enhancement (e.g. an optional "rename" feature after adding).

### 3. Embedded JSON as Mock Backend

**Decision:** JSON fixture files stored in `res/raw/` simulate a remote REST API.

**Reasoning:** The assignment explicitly allows this approach. The implementation mirrors 
what a real API would look like — separate endpoints for list and detail, with error 
handling for not-found cases. This makes the data layer swap-ready: replacing the mock 
with a real Retrofit service would only require changes in `:data:network`, with zero 
impact on any other module.

**Assumption:** The embedded JSON represents what a real REST API would return. All data 
layer logic (fetching, parsing, error handling) is structured as if it were making real 
HTTP calls.

### 4. Checkpoint Ordering

**Decision:** Checkpoints are sorted by timestamp descending (newest first).

**Reasoning:** The assignment notes that checkpoints may arrive out of order. Sorting by 
the parsed epoch value of the ISO timestamp ensures correct chronological ordering 
regardless of the order they appear in the JSON. Newest first matches the convention used 
by most parcel tracking apps (most recent event is most relevant).

### 5. Details Are Session-Only

**Decision:** Shipment details are not persisted across app restarts.

**Reasoning:** This matches the assignment requirement explicitly. The details screen 
always fetches fresh data from the mock API when opened. Only the shipments list is 
persisted via ObjectBox.

### 6. Favorites Survive Refresh

**Decision:** When the shipments list is refreshed, existing favorite states are preserved.

**Reasoning:** A refresh re-fetches the list from the mock API (which has no concept of 
favorites) and merges it with the locally persisted data. Before overwriting, the 
repository reads existing favorite states from the database and reapplies them to the 
refreshed data. This prevents a refresh from silently wiping user preferences.

---

## Technology Choices

### Jetpack Compose + Material 3
The assignment required Compose exclusively for UI. Material 3 was used for its 
comprehensive component library, theming system, and color roles that automatically adapt 
to light/dark themes.

### Dagger Hilt
Hilt was chosen for dependency injection due to its first-class Android support, 
compile-time validation, and seamless integration with ViewModels via `@HiltViewModel`. 
It eliminates manual dependency wiring while keeping the graph verifiable at build time.

### ObjectBox
ObjectBox was chosen for local persistence due to its performance characteristics with 
Android, simple entity annotation model, and built-in support for unique constraints 
(used to enforce no duplicate tracking numbers at the database level).

### kotlinx.serialization
Chosen for JSON parsing due to its native Kotlin support, compile-time safety via 
`@Serializable`, and clean integration with Kotlin data classes using `@SerialName` for 
snake_case mapping.

### Kotlin Coroutines + StateFlow
All async work runs on `Dispatchers.IO` via coroutines. UI state is modeled as a sealed 
interface exposed via `StateFlow`, consumed in composables using 
`collectAsStateWithLifecycle()` which respects the Android lifecycle and avoids 
unnecessary work when the app is in the background.

### Sealed Interface for UI State
Each screen has its own sealed interface with states: `Loading`, `Success`, `Error`, and 
where applicable `Empty`. This forces exhaustive `when` expressions in composables, 
making it impossible to accidentally forget handling a state.

### Mockito + kotlinx-coroutines-test
Used for unit testing use cases. Mockito provides mock repository instances, and 
`kotlinx-coroutines-test` provides `runTest` for testing suspend functions without 
real coroutine delays.

---

## Java Interop

The assignment required a small, focused Java component used from Kotlin. Two Java classes 
were implemented:

1. **`JsonReader`** — reads a raw JSON file from `res/raw/` and returns it as a 
   `String`. Used from Kotlin via a static method call.

2. **`DefaultTimeFormatter`** — implements the `TimeFormatter` interface. Converts ISO 8601 
   timestamps to human-readable strings using configurable input and output patterns, and 
   parses timestamps to epoch milliseconds for chronological sorting of checkpoints.

Both classes are self-contained, have no Android framework dependencies beyond `Context`, 
and are injected via Hilt through their respective interfaces.

---

## Performance Considerations

### LazyColumn with Stable Keys
The shipments list uses `LazyColumn` with `key = { it.id }` and `@Immutable` domain 
models. This ensures Compose only recomposes list items whose data actually changed — 
for example, toggling a favorite on one item does not redraw the rest of the list.

### JSON Lookup Strategy
The mock API uses `List.find` (O(n)) to locate a shipment by tracking number. A HashMap 
approach (O(1) lookup after O(n) build) was considered but rejected for the following 
reasons:
- `find` is called once per user action (adding a tracking number), not in a hot loop.
- A cached HashMap would not accurately represent a real REST API, where each request is 
  independent.
- Over-optimizing mock data access is premature and misleading as a design signal.

### Pull-to-Refresh Minimum Delay
A minimum display time of 500ms is enforced on the pull-to-refresh indicator. This 
prevents the spinner from flickering and disappearing instantly when the mock data loads 
faster than the animation can render. The use case and the delay run concurrently via 
`async`, so no artificial delay is added when real data fetching takes longer.

---

## State Preservation

- **`rememberSaveable`** is used for the tracking number input field to survive 
  configuration changes (e.g. rotation).
- **`@HiltViewModel`** ViewModels survive configuration changes automatically via the 
  Android ViewModel lifecycle.
- **`LazyColumn` keys** prevent unnecessary recomposition of unchanged list items.
- **`collectAsStateWithLifecycle`** ensures state collection respects the activity 
  lifecycle, stopping collection when the app is backgrounded.

---

## AI Utilization

AI assistance (Claude) was used during development for the following:

- Generating the extended sample fixture dataset beyond the 6 provided examples
- Generating initial composable designs for a couple screens
- Generating unit tests for domain layer
- README.md generation
- SOLUTION.md generation
- Prompting and iterating on architecture decisions

All AI-generated code was reviewed, understood, and adapted to fit the project's 
architecture and requirements before being committed.

---

## What I Would Add With More Time

- **Search bar** on the shipments list screen
- **Remove tracking** — allow users to remove a shipment from their list
- **Dynamic color** — Material You dynamic color support on Android 12+
- **Microinteractions** — animated status chips, favorite toggle animation
- **Real network layer** — swap the mock with a Retrofit implementation behind the same 
  interface with zero changes to domain or app modules
