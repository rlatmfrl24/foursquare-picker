package com.soulkey.fspicker.lib.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VenueTipItem(
    val id: String,
    val text: String,
    val user: User
): Parcelable