package com.soulkey.fspicker.lib.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VenueContact(
    val phone: String?,
    val formattedPhone: String?
) : Parcelable