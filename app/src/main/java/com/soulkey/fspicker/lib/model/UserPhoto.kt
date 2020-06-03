package com.soulkey.fspicker.lib.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserPhoto(
    val prefix: String,
    val suffix: String
): Parcelable