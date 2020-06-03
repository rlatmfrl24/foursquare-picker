package com.soulkey.fspicker.ui.detail

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.soulkey.fspicker.lib.api.FoursquareAPIClient
import com.soulkey.fspicker.lib.model.Tip
import com.soulkey.fspicker.lib.model.VenueDetail
import io.reactivex.disposables.Disposable
import timber.log.Timber

class VenueDetailViewModel(private val client: FoursquareAPIClient, private val context: Context) : ViewModel() {
    val venueName = MutableLiveData("Venue Name")
    val venueAddress = MutableLiveData("Venue Address")
    val venuePhone = MutableLiveData("Unknown")
    val venueLikeCount = MutableLiveData("0")
    val venueRating = MutableLiveData("0.0")
    val venuePhotoUrl = MutableLiveData<String>()
    val tipsList = MutableLiveData<List<Tip>>()

    fun setBasicData(name: String, address: String) {
        venueName.value = name
        venueAddress.value = address
    }

    private fun parseVenueData(data: VenueDetail) {
        data.contact.formattedPhone?.let {
            venuePhone.value = data.contact.formattedPhone
        }
        venueLikeCount.value = data.likes?.count.toString()
        venueRating.value = data.rating

        Timber.v("diver:/ ${data.contact}")

        val venuePhotoData = data.photos?.groups?.get(0)?.items?.get(0)
        venuePhotoData?.let {
            venuePhotoUrl.value = venuePhotoData.prefix+"500x500"+venuePhotoData.suffix
        }

        val venueTipData = data.tips?.groups?.get(0)?.items
        venueTipData?.let {tips->
            val itemList = tips.map { item->
                var userName = item.user.firstName
                if (item.user.lastName != null) userName += ", ${item.user.lastName}"
                val userPhotoUrl = item.user.photo?.prefix + "36x36" + item.user.photo?.suffix
                val description = item.text
                val userId = item.user.id
                Tip(userId, userPhotoUrl, userName, description)
            }
            tipsList.value = itemList
        }
    }

    fun requestVenueDetail(fsId: String): Disposable {
        return client.getVenueDetail(fsId).subscribe { body ->
            if (body.meta.code == "200") {
                val data = body.response
                parseVenueData(data.venue)
            } else {
                if (body.meta.errorDetail != null){
                    Toast.makeText(context, body.meta.errorDetail, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}