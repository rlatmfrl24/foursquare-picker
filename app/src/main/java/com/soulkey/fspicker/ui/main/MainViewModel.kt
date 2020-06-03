package com.soulkey.fspicker.ui.main

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.soulkey.fspicker.lib.data.RecommendedVenueRepository
import com.soulkey.fspicker.lib.api.FoursquareAPIClient
import io.reactivex.disposables.Disposable
import timber.log.Timber

class MainViewModel(private val recommendedVenueRepository: RecommendedVenueRepository, private val client: FoursquareAPIClient, private val context: Context): ViewModel() {
    private val currentLL: MutableLiveData<Pair<Double, Double>> = MutableLiveData()
    val currentLocation: MutableLiveData<String> = MutableLiveData("Unknown")
    val isVenuesLoaded = MutableLiveData(false)
    val recommendedVenues = recommendedVenueRepository.getAllVenues()

    fun clearVenues(){
        recommendedVenueRepository.clearVenues()
    }

    fun updateCurrentLL(latitude: Double, longitude: Double): Disposable{
        currentLL.value = Pair(latitude, longitude)
        return client.getRecommendVenues(latitude, longitude).subscribe { body->
            if (body.meta.code == "200"){
                val data = body.response
                val recommendVenus = data.groups[0].items
                currentLocation.value = data.headerFullLocation
                recommendedVenueRepository.updateVenues(recommendVenus)
                isVenuesLoaded.value = true
            } else {
                if (body.meta.errorDetail != null){
                    Toast.makeText(context, body.meta.errorDetail, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}