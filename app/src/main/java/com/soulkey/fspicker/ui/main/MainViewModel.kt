package com.soulkey.fspicker.ui.main

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.soulkey.fspicker.config.Constant.DEFAULT_LATITUDE
import com.soulkey.fspicker.config.Constant.DEFAULT_LONGITUDE
import com.soulkey.fspicker.config.Constant.PREF_NAME_RECENT_NAME
import com.soulkey.fspicker.lib.data.RecommendedVenueRepository
import com.soulkey.fspicker.lib.api.FoursquareAPIClient
import io.reactivex.disposables.Disposable
import timber.log.Timber

class MainViewModel(private val recommendedVenueRepository: RecommendedVenueRepository, private val client: FoursquareAPIClient, private val context: Context): ViewModel() {
    private val currentLL: MutableLiveData<Pair<Double, Double>> = MutableLiveData()
    private var recentLocationPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME_RECENT_NAME, Context.MODE_PRIVATE)
    val currentLocation: MutableLiveData<String> = MutableLiveData("Unknown")
    val isVenuesLoading = MutableLiveData(false)
    val recommendedVenues = recommendedVenueRepository.getAllVenues()

    fun getRecentLocationData() {
        val recentLocationName = recentLocationPreferences.getString("name", "Unknown")
        currentLocation.value = recentLocationName
    }

    fun getRecentLL(): Pair<Float, Float> {
        val recentLocationLatitude = recentLocationPreferences.getFloat("latitude", DEFAULT_LATITUDE.toFloat())
        val recentLocationLongitude = recentLocationPreferences.getFloat("longitude", DEFAULT_LONGITUDE.toFloat())
        return Pair(recentLocationLatitude, recentLocationLongitude)
    }

    fun loadRecentLocationName() {
        currentLocation.value = recentLocationPreferences.getString("name", "Unknown")
    }

    private fun setRecentLocationData(name: String, latitude: Double, longitude: Double) {
        recentLocationPreferences.edit().also { editor ->
            editor.putString("name", name)
            editor.putFloat("latitude", latitude.toFloat())
            editor.putFloat("longitude", longitude.toFloat())
        }.apply()
    }

    fun updateCurrentLL(latitude: Double, longitude: Double): Disposable{
        currentLL.value = Pair(latitude, longitude)
        return client.getRecommendVenues(latitude, longitude).subscribe(
            { body ->
                if (body.meta.code == "200") {
                    val data = body.response
                    val recommendVenus = data.groups[0].items
                    currentLocation.value = data.headerFullLocation
                    recommendedVenueRepository.updateVenues(recommendVenus)
                    setRecentLocationData(data.headerFullLocation, latitude, longitude)
                } else {
                    if (body.meta.errorDetail != null) {
                        Toast.makeText(context, body.meta.errorDetail, Toast.LENGTH_SHORT).show()
                    }
                }
                isVenuesLoading.value = false
            }, {
                Timber.v("diver:/ ${it.localizedMessage}")
                isVenuesLoading.value = false
                Toast.makeText(context, "API Connection Error..", Toast.LENGTH_SHORT).show()
            })
    }
}