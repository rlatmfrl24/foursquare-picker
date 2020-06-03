package com.soulkey.fspicker.lib.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VenueLocation(
    val id: String,
    val address: String,
    val formattedAddress: MutableList<String>
): Parcelable