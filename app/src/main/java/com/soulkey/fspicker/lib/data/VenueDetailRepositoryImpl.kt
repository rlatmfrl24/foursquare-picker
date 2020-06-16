package com.soulkey.fspicker.lib.data

import com.soulkey.fspicker.lib.api.FoursquareAPIClient
import com.soulkey.fspicker.lib.api.VenueDetailResponse
import io.reactivex.Single

class VenueDetailRepositoryImpl(private val client: FoursquareAPIClient) : VenueDetailRepository{
    override fun requestVenueDetail(fsId: String): Single<VenueDetailResponse> {
        return client.getVenueDetail(fsId)
    }
}