package com.soulkey.fspicker.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.soulkey.fspicker.model.Location

@Database(entities = [Location::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun locationDao(): LocationDao
}