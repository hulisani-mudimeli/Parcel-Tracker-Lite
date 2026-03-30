package com.offerzen.parceltrackerlite.ui

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview


@Preview(
    showBackground = true,
    name = "day mode",
    uiMode = UI_MODE_NIGHT_NO
)
annotation class DayPreview

@Preview(
    showBackground = true,
    name = "night mode",
    uiMode = UI_MODE_NIGHT_YES
)
annotation class NightPreview


@DayPreview
@NightPreview
annotation class DayNightPreviews

@Preview(
    name = "Phone Preview",
    showSystemUi = true,
    device = Devices.PIXEL_7,
    showBackground = true,
    uiMode = UI_MODE_NIGHT_NO
)
annotation class PhoneLightPreview

@Preview(
    name = "Phone Preview",
    showSystemUi = true,
    device = Devices.PIXEL_7,
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES
)
annotation class PhoneDarkPreview

@PhoneLightPreview
@PhoneDarkPreview
annotation class PhonePreviews