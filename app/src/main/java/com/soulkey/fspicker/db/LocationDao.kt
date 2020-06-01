package com.soulkey.fspicker.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.soulkey.fspicker.model.Location

@Dao
interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setCurrentLL(currentLocation: Location)

    @Query("SELECT * FROM tb_location WHERE id = 1 LIMIT 1")
    fun getCurrentLL(): LiveData<List<Location>>
}