package com.soulkey.fspicker.lib.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RecommendData(
    val headerFullLocation: String,
    val totalResults: Number,
    val groups: MutableList<RecommendGroup>
): Parcelable