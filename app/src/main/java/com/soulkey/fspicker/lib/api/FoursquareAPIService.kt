package com.soulkey.fspicker.lib.api

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FoursquareAPIService {

    @GET("v2/venues/explore")
    fun getRecommendByLL(
        @Query("ll") ll: String,
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String,
        @Query("v") version: String
    ): Single<Response<RecommendVenueResponse>>

    @GET("v2/venues/{VENUE_ID}")
    fun getDetailById(
        @Path("VENUE_ID") fsId: String,
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String,
        @Query("v") version: String
    ): Single<Response<VenueDetailResponse>>
}