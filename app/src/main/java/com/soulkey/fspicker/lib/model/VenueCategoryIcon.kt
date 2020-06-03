package com.soulkey.fspicker.lib.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VenueCategoryIcon(
    val prefix: String,
    val suffix: String
): Parcelable