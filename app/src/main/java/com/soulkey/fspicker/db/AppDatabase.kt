package com.soulkey.fspicker.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.soulkey.fspicker.model.RecommendedVenue

@Database(entities = [RecommendedVenue::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun recommendedVenueDao(): RecommendedVenueDao
}