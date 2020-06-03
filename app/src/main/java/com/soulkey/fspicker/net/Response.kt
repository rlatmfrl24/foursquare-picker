package com.soulkey.fspicker.net

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class FoursquareResponse (
    @SerializedName("meta")
    val meta: JsonObject,
    @SerializedName("response")
    val response: JsonObject
)