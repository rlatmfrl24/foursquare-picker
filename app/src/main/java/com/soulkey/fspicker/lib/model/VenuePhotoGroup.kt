package com.soulkey.fspicker.lib.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VenuePhotoGroup(
    val count: Number,
    val items: MutableList<VenuePhotoItem>
): Parcelable