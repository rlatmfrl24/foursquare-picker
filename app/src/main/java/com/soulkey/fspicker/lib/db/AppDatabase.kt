package com.soulkey.fspicker.lib.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.soulkey.fspicker.lib.model.RecommendedVenue

@Database(entities = [RecommendedVenue::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun recommendedVenueDao(): RecommendedVenueDao
}