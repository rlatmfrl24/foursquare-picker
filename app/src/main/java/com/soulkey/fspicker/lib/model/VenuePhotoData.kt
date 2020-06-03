package com.soulkey.fspicker.lib.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VenuePhotoData(
    val count: Number,
    val groups: MutableList<VenuePhotoGroup>
): Parcelable