package com.soulkey.fspicker.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_location")
data class Location(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "latitude")
    val currentLatitude: Double,
    @ColumnInfo(name = "longitude")
    val currentLongitude: Double,
    @ColumnInfo(name = "headerLocation")
    val currentHeaderLocation: String?
)