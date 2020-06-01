package com.soulkey.fspicker.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.soulkey.fspicker.data.LocationRepository
import com.soulkey.fspicker.lib.FoursquareClient
import com.soulkey.fspicker.lib.RecommandVenuesResponse
import io.reactivex.Single
import retrofit2.Response

class MainViewModel(private val locationRepository: LocationRepository, private val client: FoursquareClient): ViewModel() {
    val currentLocation: MutableLiveData<String> = MutableLiveData()
    val currentLL: MutableLiveData<Pair<Double, Double>> = MutableLiveData()

    fun updateCurrentLL(latitude: Double, longitude: Double){
        currentLL.value = Pair(latitude, longitude)
    }

    fun updateCurrentLocation(location: String){
        currentLocation.value = location
    }

    fun callRecommendVenues(latitude: Double, longitude: Double): Single<Response<RecommandVenuesResponse>> {
        return client.getRecommendVenues(latitude, longitude)
    }
}