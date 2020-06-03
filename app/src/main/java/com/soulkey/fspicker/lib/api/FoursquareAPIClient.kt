package com.soulkey.fspicker.lib.api

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import retrofit2.Response

class FoursquareAPIClient(private val apiService: FoursquareAPIService) {
    fun getRecommendVenues(
        latitude: Double,
        longitude: Double
    ): Single<RecommendVenueResponse> =
        apiService.getRecommendByLL("$latitude,$longitude")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { t -> if (t.isSuccessful) t.body() else throw HttpException(t) }


    fun getVenueDetail(fsId: String): Single<VenueDetailResponse> =
        apiService.getDetailById(fsId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { t -> if (t.isSuccessful) t.body() else throw HttpException(t) }
}