package com.soulkey.fspicker.lib.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Venue(
    val id: String,
    val name: String,
    val location: VenueLocation,
    val categories: MutableList<VenueCategory>
): Parcelable