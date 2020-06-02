package com.soulkey.fspicker.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_recommended_venue")
data class RecommendedVenue(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pid")
    val pid: Long?,
    @ColumnInfo(name = "fsId")
    val fsId: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "address")
    val address: String,
    @ColumnInfo(name = "iconLink")
    val iconLink: String?
)