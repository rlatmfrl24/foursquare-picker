package com.soulkey.fspicker.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.soulkey.fspicker.lib.FoursquareClient
import io.reactivex.disposables.Disposable
import timber.log.Timber

class VenueDetailViewModel(private val client: FoursquareClient) : ViewModel() {
    val venueName = MutableLiveData("Venue Name")
    val venueAddress = MutableLiveData("Venue Address")
    val venuePhone = MutableLiveData("Venue Phone")
    val venueLikeCount = MutableLiveData("0")
    val venueRating = MutableLiveData("0.0")

    fun setBasicData(name: String, address: String) {
        venueName.value = name
        venueAddress.value = address
    }

    private fun parseData(data: JsonObject) {
        //contact
        if (data.has("contact")) {
            val venueContactData = data.get("contact").asJsonObject
            Timber.v("diver:/ contact data:  $venueContactData")
            if (venueContactData.has("phone")) {
                venuePhone.value = venueContactData["phone"].asString
            }
        }

        //location(Not Necessary)
        if (data.has("location")) {
            val venueLocationData = data.get("location").asJsonObject
        }

        //like
        if (data.has("likes")) {
            val venueLikeData = data.get("likes").asJsonObject
            Timber.v("diver:/ contact data:  $venueLikeData")
            if (venueLikeData.has("count")) {
                venueLikeCount.value = venueLikeData["count"].asString
            }
        }

        //rating
        if (data.has("rating")) {
            venueRating.value = data.get("rating").asString
        }

        //photos
        if (data.has("photos")) {
            val venuePhotoData = data.get("photos").asJsonObject
        }

        //tips
        if (data.has("tips")) {
            val venueTipsData = data.get("tips").asJsonObject
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