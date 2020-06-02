package com.soulkey.fspicker.ui.detail

import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.soulkey.fspicker.lib.FoursquareClient
import io.reactivex.disposables.Disposable
import timber.log.Timber

class VenueDetailViewModel(private val client: FoursquareClient) : ViewModel() {
    private fun parseData(data: JsonObject) {
        //contact
        if (data.has("contact")) {
            val venueContactData = data.get("contact").asJsonObject
            Timber.v("diver:/ contact data:  $venueContactData")
        }

        //location
        if (data.has("location")) {
            val venueLocationData = data.get("location").asJsonObject
        }

        //price
        if (data.has("price")) {
            val venuePriceData = data.get("price").asJsonObject
        }

        //like
        if (data.has("likes")) {
            val venueLikeData = data.get("likes").asJsonObject
        }

        //rating
        if (data.has("rating")) {
            val venueRating = data.get("rating").asString
        }

        //photos
        if (data.has("photos")) {
            val venuePhotoData = data.get("photos").asJsonObject

        }
    }

    fun requestVenueDetail(fsId: String): Disposable {
        return client.getVenueDetail(fsId).subscribe { response ->
            if (response.isSuccessful) {
                val venueData = response.body()?.response?.asJsonObject?.get("venue")?.asJsonObject
                venueData?.let { parseData(it) }
            } else {
                Timber.v(response.errorBody().toString())
            }
        }
    }

}