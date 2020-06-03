package com.soulkey.fspicker.lib.api

import android.os.Parcelable
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import com.soulkey.fspicker.lib.model.RecommendData
import com.soulkey.fspicker.lib.model.VenueDetail
import com.soulkey.fspicker.lib.model.VenueDetailWrapper
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class RecommendVenueResponse(
    val meta: FoursquareMeta,
    val response: RecommendData
): Parcelable

@Parcelize
data class VenueDetailResponse (
    val meta: FoursquareMeta,
    val response: VenueDetailWrapper
): Parcelable

@Parcelize
data class FoursquareMeta(
    val code: String,
    val errorDetail: String?
): Parcelable