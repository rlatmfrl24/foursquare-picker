package com.soulkey.fspicker.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.soulkey.fspicker.data.RecommendedVenueRepository
import com.soulkey.fspicker.lib.FoursquareClient
import io.reactivex.disposables.Disposable

class MainViewModel(private val recommendedVenueRepository: RecommendedVenueRepository, private val client: FoursquareClient): ViewModel() {
    private val currentLL: MutableLiveData<Pair<Double, Double>> = MutableLiveData()
    val currentLocation: MutableLiveData<String> = MutableLiveData("Unknown")
    val recommendedVenues = recommendedVenueRepository.getAllVenues()

    fun clearVenues(){
        recommendedVenueRepository.clearVenues()
    }

    fun updateCurrentLL(latitude: Double, longitude: Double): Disposable{
        currentLL.value = Pair(latitude, longitude)
        return client.getRecommendVenues(latitude, longitude).subscribe { response->
            if (response.isSuccessful){
                val responseLocation = response.body()?.response?.get("headerLocation")?.asString
                val recommendedVenues =
                    response.body()?.response?.
                    get("groups")?.asJsonArray?.
                    get(0)?.asJsonObject?.
                    get("items")?.asJsonArray
                currentLocation.value = responseLocation
                recommendedVenues?.let {
                    recommendedVenueRepository.updateVenues(it)
                }
            }
        }
    }
}