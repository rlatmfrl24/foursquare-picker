package com.soulkey.fspicker.lib.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VenueTipData(
    val count: Number,
    val groups: MutableList<VenueTipGroup>
): Parcelable