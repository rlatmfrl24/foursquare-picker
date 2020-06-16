package com.soulkey.fspicker.lib.data

import com.soulkey.fspicker.lib.api.VenueDetailResponse
import io.reactivex.Single

interface VenueDetailRepository {
    fun requestVenueDetail(fsId: String): Single<VenueDetailResponse>

}