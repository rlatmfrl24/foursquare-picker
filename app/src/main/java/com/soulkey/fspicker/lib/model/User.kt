package com.soulkey.fspicker.lib.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val id: String,
    val firstName: String,
    val lastName: String?,
    val photo: UserPhoto?
): Parcelable