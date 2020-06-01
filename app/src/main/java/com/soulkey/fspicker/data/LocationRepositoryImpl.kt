package com.soulkey.fspicker.data

import androidx.lifecycle.LiveData
import com.google.android.gms.tasks.Task
import com.soulkey.fspicker.db.LocationDao
import com.soulkey.fspicker.model.Location

class LocationRepositoryImpl (private val locationDao: LocationDao): LocationRepository{
    override fun setCurrentLL(latitude: Double, longitude: Double) {
        locationDao.setCurrentLL(Location(1, latitude, longitude, null))
    }

    override fun getCurrentLL(): LiveData<List<Location>> {
        return locationDao.getCurrentLL()
    }
}