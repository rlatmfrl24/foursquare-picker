package com.soulkey.fspicker.lib

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class FoursquareClient (private val api: FoursquareAPI) {
    fun getRecommendVenues(latitude: Double, longitude: Double): Single<Response<FoursquareResponse>>{
        return api.getRecommendByLL("$latitude,$longitude")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getVenuePhotos(fsId: String): Single<Response<FoursquareResponse>> {
        return api.getPhotosById(fsId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}