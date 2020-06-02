package com.soulkey.fspicker.data

import androidx.lifecycle.LiveData
import com.google.gson.JsonArray
import com.soulkey.fspicker.model.RecommendedVenue

interface RecommendedVenueRepository {
    fun updateVenues(data: JsonArray)
    fun clearVenues()
    fun getAllVenues(): LiveData<List<RecommendedVenue>>
}