package com.duoc.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "legislative_changes")
data class LegislativeChangeEntity(
    @PrimaryKey val id: String,
    val title: String,
    val date: String,
    val summary: String,
    val currentSegmentsJson: String,
    val previousSegmentsJson: String
)