package com.soulkey.fspicker.lib

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class FoursquareResponse (
    @SerializedName("meta")
    val meta: JsonObject,
    @SerializedName("response")
    val response: JsonObject
)