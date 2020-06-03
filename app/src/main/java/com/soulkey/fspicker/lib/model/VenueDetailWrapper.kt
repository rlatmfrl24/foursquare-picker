package com.soulkey.fspicker.lib.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VenueDetailWrapper(
    val venue: VenueDetail
): Parcelable