package com.soulkey.fspicker.lib.api

import android.content.Context
import com.soulkey.fspicker.R
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class FoursquareAPIClient(private val apiService: FoursquareAPIService, context: Context) {

    private val clientId = context.getString(R.string.client_id)
    private val clientSecret = context.getString(R.string.client_secret)
    private val version = context.getString(R.string.version)

    fun getRecommendVenues(
        latitude: Double,
        longitude: Double
    ): Single<RecommendVenueResponse> =
        apiService.getRecommendByLL("$latitude,$longitude", clientId, clientSecret, version)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { t -> if (t.isSuccessful) t.body() else throw HttpException(t) }


    fun getVenueDetail(fsId: String): Single<VenueDetailResponse> =
        apiService.getDetailById(fsId, clientId, clientSecret, version)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { t -> if (t.isSuccessful) t.body() else throw HttpException(t) }
}