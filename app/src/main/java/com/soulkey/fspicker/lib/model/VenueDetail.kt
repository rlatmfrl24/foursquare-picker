package com.soulkey.fspicker.lib.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VenueDetail(
    val id: String,
    val name: String,
    val contact: VenueContact,
    val location: VenueLocation?,
    val categories: MutableList<VenueCategory>?,
    val likes: VenueLikes?,
    val rating: String?,
    val photos: VenuePhotoData?,
    val tips: VenueTipData?
): Parcelable