package com.soulkey.fspicker.lib.api

import com.soulkey.fspicker.config.Constant.FOURSQUARE_BASIC_PARAM
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FoursquareAPI {

    @GET("v2/venues/explore?$FOURSQUARE_BASIC_PARAM")
    fun getRecommendByLL(@Query("ll") ll: String): Single<Response<FoursquareResponse>>

    @GET("v2/venues/{VENUE_ID}?$FOURSQUARE_BASIC_PARAM")
    fun getDetailById(@Path("VENUE_ID") fsId: String): Single<Response<FoursquareResponse>>
}