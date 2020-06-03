package com.soulkey.fspicker.lib.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.soulkey.fspicker.lib.model.RecommendedVenue

@Dao
interface RecommendedVenueDao {
    @Query("SELECT * FROM tb_recommended_venue")
    fun getAllVenues(): LiveData<List<RecommendedVenue>>

    @Insert
    fun insertVenue(venue: RecommendedVenue)

    @Insert
    fun insertVenues(venues: List<RecommendedVenue>)

    @Query("DELETE FROM tb_recommended_venue")
    fun deleteAll()

    @Transaction
    fun updateVenues(venues: List<RecommendedVenue>){
        deleteAll()
        insertVenues(venues)
    }


}