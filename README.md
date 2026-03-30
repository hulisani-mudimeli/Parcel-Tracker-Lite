# ParcelTrackerLite

A consumer Android app that lets users track parcel deliveries. Users can add tracking numbers, view a consolidated list of shipments with current status, and open a details screen to see a delivery timeline.

## Tech Stack

- **Language:** Kotlin + Java (interop)
- **UI:** Jetpack Compose (Material 3)
- **Architecture:** Clean Architecture (multi-module)
- **DI:** Dagger Hilt
- **Database:** ObjectBox
- **Serialization:** kotlinx.serialization
- **State Management:** StateFlow + sealed interfaces

## Module Structure
```
ParcelTrackerLite/
├── app/                        # Entry point, navigation & UI Layer
├── domain/                     # Use cases, repository interfaces, 
├── common/                     # Common items used across all modules. Consists: helpers, enums, generic result & domain models
└── data/
    ├── database/               # ObjectBox entities and local data source
    ├── network/                # Mock API service, JSON fixtures, DTOs
    └── repository/             # Repository implementations, DI bindings
```

## Prerequisites

- Android Studio Hedgehog (2023) or newer
- Java 17 or newer
- Android SDK with minimum API 24
- Gradle 8.x

## Running the App

1. **Clone the repository:**
```bash
git clone https://github.com/hulisani-mudimeli/Parcel-Tracker-Lite.git
cd ParcelTrackerLite
```

2. **Open in Android Studio:**
   - Open Android Studio
   - Select `File → Open`
   - Navigate to the cloned directory and select it

3. **Let Gradle sync:**
   - Android Studio will automatically start syncing
   - Wait for the sync to complete before proceeding
   - If sync fails, select `File → Sync Project with Gradle Files`

4. **Run the app:**
   - Connect a physical device or start an emulator (API 24+)
   - Click the **Run** button or press `Shift + F10`
   - Select your device and click **OK**

## Features

- **Add Tracking Number** — enter a tracking number to add a shipment to your list
- **Shipments List** — view all tracked shipments with status, carrier, and last update time
- **Shipment Details** — view a full timeline of checkpoints for a selected shipment
- **Favorites** — mark/unmark shipments as favorites
- **Search** — filter shipments by title or tracking number
- **Pull to Refresh** — pull down on the list to refresh shipments
- **Offline Support** — last known shipments list is persisted and shown when offline

## Data Source

The app uses embedded JSON fixture files as a mock backend located in:
```
data/network/src/main/res/raw/
├── shipment_list.json
└── shipment_details.json
```

These fixtures simulate a real REST API. Adding a tracking number looks up the fixture data — if the tracking number is not found, an error is shown.

## Test Tracking Numbers

Use these tracking numbers to test the app:

| Tracking Number | Carrier | Title | Status |
|---|---|---|---|
| 1Z999AA10123456784 | Acme Express | Laptop order | In Transit |
| 9400111899223197428499 | Universal Post | Shoes | Delivered |
| EV123456789CN | SkyNet Logistics | Camera | Out for Delivery |
| JJ000123456US | ParcelGo | Coffee beans | In Transit |
| LV987654321US | Acme Express | Headphones | Exception |
| GM555555555US | Universal Post | T-shirt | Created |

## Notes

- Details screen data is session-only and does not persist across app restarts
- Duplicate tracking numbers are rejected
- Checkpoints are sorted chronologically, newest first
