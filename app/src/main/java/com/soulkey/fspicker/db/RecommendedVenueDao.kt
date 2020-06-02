package com.soulkey.fspicker.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.soulkey.fspicker.model.RecommendedVenue
import io.reactivex.Single

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