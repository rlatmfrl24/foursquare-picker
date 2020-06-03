package com.soulkey.fspicker.lib.data

import androidx.lifecycle.LiveData
import com.google.gson.JsonArray
import com.soulkey.fspicker.lib.db.RecommendedVenueDao
import com.soulkey.fspicker.lib.model.RecommendItem
import com.soulkey.fspicker.lib.model.RecommendedVenue

class RecommendedVenueRepositoryImpl (private val recommendedVenueDao: RecommendedVenueDao): RecommendedVenueRepository{
    override fun updateVenues(data: MutableList<RecommendItem>) {
        val venueList = data.map {item->
            val venue = item.venue
            val venueId = venue.id
            val venueName = venue.name
            val venueFullAddress = venue.location.formattedAddress.joinToString (" ")
            val venueCategory = venue.categories[0].icon
            val venueCategoryIconUrl = venueCategory.prefix + "bg_32" + venueCategory.suffix
            RecommendedVenue(null, venueId, venueName, venueFullAddress, venueCategoryIconUrl)
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