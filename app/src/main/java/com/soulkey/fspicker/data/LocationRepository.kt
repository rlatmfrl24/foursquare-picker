package com.soulkey.fspicker.data

import androidx.lifecycle.LiveData
import com.soulkey.fspicker.model.Location

interface LocationRepository {
    fun setCurrentLL(latitude: Double, longitude: Double)
    fun getCurrentLL(): LiveData<List<Location>>
}