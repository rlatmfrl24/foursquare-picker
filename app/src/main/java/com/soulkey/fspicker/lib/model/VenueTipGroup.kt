package com.soulkey.fspicker.lib.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VenueTipGroup(
    val name: String,
    val count: Number,
    val items: MutableList<VenueTipItem>
): Parcelable