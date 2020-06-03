package com.soulkey.fspicker.ui.main

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.soulkey.fspicker.data.RecommendedVenueRepository
import com.soulkey.fspicker.net.FoursquareClient
import io.reactivex.disposables.Disposable
import timber.log.Timber

class MainViewModel(private val recommendedVenueRepository: RecommendedVenueRepository, private val client: FoursquareClient, private val context: Context): ViewModel() {
    private val currentLL: MutableLiveData<Pair<Double, Double>> = MutableLiveData()
    val currentLocation: MutableLiveData<String> = MutableLiveData("Unknown")
    val isVenuesLoaded = MutableLiveData(false)
    val recommendedVenues = recommendedVenueRepository.getAllVenues()

    fun clearVenues(){
        recommendedVenueRepository.clearVenues()
    }

    fun updateCurrentLL(latitude: Double, longitude: Double): Disposable{
        currentLL.value = Pair(latitude, longitude)
        return client.getRecommendVenues(latitude, longitude).subscribe { response->
            if (response.isSuccessful){
                val apiStatusCode = response.body()!!.meta["code"].asString
                if (apiStatusCode == "200"){
                    val responseLocation = response.body()?.response?.get("headerFullLocation")?.asString
                    val recommendedVenues =
                        response.body()?.response?.
                        get("groups")?.asJsonArray?.
                        get(0)?.asJsonObject?.
                        get("items")?.asJsonArray
                    currentLocation.value = responseLocation
                    isVenuesLoaded.value = true
                    recommendedVenues?.let {
                        recommendedVenueRepository.updateVenues(it)
                    }
                }
                else {
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