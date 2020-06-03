package com.soulkey.fspicker.lib.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VenueCategory(
    val id: String,
    val name: String,
    val icon: VenueCategoryIcon
): Parcelable