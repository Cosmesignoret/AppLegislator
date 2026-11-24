package com.duoc.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val remoteId: String? = null,
    val isSynced: Boolean = false,
    val title: String,
    val content: String,
    val legalReference: String,
    val dateText: String,
    val imageBase64: String? = null
)