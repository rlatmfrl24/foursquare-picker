package com.soulkey.fspicker.ui.detail

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.soulkey.fspicker.net.FoursquareClient
import com.soulkey.fspicker.model.Tip
import io.reactivex.disposables.Disposable
import timber.log.Timber

class VenueDetailViewModel(private val client: FoursquareClient, private val context: Context) : ViewModel() {
    val venueName = MutableLiveData("Venue Name")
    val venueAddress = MutableLiveData("Venue Address")
    val venuePhone = MutableLiveData("Venue Phone")
    val venueLikeCount = MutableLiveData("0")
    val venueRating = MutableLiveData("0.0")
    val venuePhotoUrl = MutableLiveData<String>()
    val tipsList = MutableLiveData<List<Tip>>()

    fun setBasicData(name: String, address: String) {
        venueName.value = name
        venueAddress.value = address
    }

    private fun parseVenueData(data: JsonObject) {
        //contact
        if (data.has("contact")) {
            val venueContactData = data.get("contact").asJsonObject
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
            if (venuePhotoData["count"].asInt > 0 && venuePhotoData.has("groups")){
                val photo = venuePhotoData["groups"].asJsonArray.get(0).asJsonObject.get("items").asJsonArray.get(0).asJsonObject
                venuePhotoUrl.value = "${photo["prefix"].asString}500x500${photo["suffix"].asString}"
            }
        }

        //tips
        if (data.has("tips")) {
            val venueTipsData = data.get("tips").asJsonObject
            if (venueTipsData["count"].asInt > 0 && venueTipsData.has("groups")) {
                val tipItemList = venueTipsData["groups"].asJsonArray[0].asJsonObject["items"].asJsonArray
                val itemList = tipItemList.map { tipData->
                    val item = tipData.asJsonObject
                    val userData = item["user"].asJsonObject
                    val userId = userData["id"].asString
                    var userName = "Unknown"
                    if (userData.has("firstName")) userName = userData["firstName"].asString
                    if (userData.has("lastName")) userName += ", ${userData["lastName"].asString}"
                    val userPhotoData = userData["photo"].asJsonObject
                    val photoUrl = "${userPhotoData["prefix"].asString}32x32${userPhotoData["suffix"].asString}"
                    val description = item["text"].asString
                    Tip(userId, photoUrl, userName, description)
                }
                tipsList.value = itemList
            }
        }
    }

    fun requestVenueDetail(fsId: String): Disposable {
        return client.getVenueDetail(fsId).subscribe { response ->
            if (response.isSuccessful) {
                val apiStatusCode = response.body()!!.meta["code"].asString
                if (apiStatusCode == "200"){
                    val venueData = response.body()?.response?.asJsonObject?.get("venue")?.asJsonObject
                    venueData?.let { parseVenueData(it) }
                }else {
                    //Error Toast
                    val metaData = response.body()!!.meta.asJsonObject
                    Timber.v(response.body()!!.meta.toString())
                    if (metaData.has("errorDetail")){
                        Toast.makeText(context, metaData["errorDetail"].asString, Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Timber.v(response.errorBody().toString())
            }
        }
    }
}