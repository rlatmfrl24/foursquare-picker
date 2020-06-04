package com.soulkey.fspicker.lib.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Tip(
    val id: String,
    val iconLink: String,
    val userName: String,
    val description: String
): Parcelable