package com.soulkey.fspicker.lib.data

import androidx.lifecycle.LiveData
import com.google.gson.JsonArray
import com.soulkey.fspicker.lib.model.RecommendItem
import com.soulkey.fspicker.lib.model.RecommendedVenue

interface RecommendedVenueRepository {
    fun updateVenues(data: MutableList<RecommendItem>)
    fun clearVenues()
    fun getAllVenues(): LiveData<List<RecommendedVenue>>
}