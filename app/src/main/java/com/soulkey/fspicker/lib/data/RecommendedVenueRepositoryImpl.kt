package com.soulkey.fspicker.lib.data

import androidx.lifecycle.LiveData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.gson.JsonArray
import com.soulkey.fspicker.lib.api.FoursquareAPIClient
import com.soulkey.fspicker.lib.api.RecommendVenueResponse
import com.soulkey.fspicker.lib.db.RecommendedVenueDao
import com.soulkey.fspicker.lib.model.RecommendItem
import com.soulkey.fspicker.lib.model.RecommendedVenue
import io.reactivex.Single

class RecommendedVenueRepositoryImpl (private val recommendedVenueDao: RecommendedVenueDao, private val client: FoursquareAPIClient): RecommendedVenueRepository{
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

    override fun requestRecommendVenus(latitude: Double, longitude: Double): Single<RecommendVenueResponse> {
        return client.getRecommendVenues(latitude, longitude)
    }
}