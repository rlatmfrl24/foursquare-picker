package com.soulkey.fspicker.data

import androidx.lifecycle.LiveData
import com.google.gson.JsonArray
import com.soulkey.fspicker.db.RecommendedVenueDao
import com.soulkey.fspicker.lib.FoursquareClient
import com.soulkey.fspicker.model.RecommendedVenue
import timber.log.Timber

class RecommendedVenueRepositoryImpl (private val recommendedVenueDao: RecommendedVenueDao): RecommendedVenueRepository{
    override fun updateVenues(data: JsonArray) {
        val venueList = data.map {
            val venue = it.asJsonObject.get("venue").asJsonObject
            val venueId = venue["id"].asString
            val venueName = venue["name"].asString
            val venueAddress =
                venue["location"].asJsonObject.get("formattedAddress").asJsonArray.joinToString(" ") { addressItem ->
                    addressItem.asString
                }
            val venueCategories = venue["categories"].asJsonArray
            var firstCategory = ""
            if (venueCategories.size() > 0) {
                firstCategory = venueCategories.get(0).asJsonObject.get("icon").asJsonObject.get("prefix").asString + "bg_32.png"
            }
            RecommendedVenue(null, venueId, venueName, venueAddress, firstCategory)
        }
        recommendedVenueDao.updateVenues(venueList)
    }

    override fun clearVenues() {
        recommendedVenueDao.deleteAll()
    }

    override fun getAllVenues(): LiveData<List<RecommendedVenue>> {
        return recommendedVenueDao.getAllVenues()
    }
}