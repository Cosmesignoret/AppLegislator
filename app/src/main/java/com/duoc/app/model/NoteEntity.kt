package com.duoc.app.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Representa la tabla 'notes' en la base de datos local SQLite.
 */
@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val content: String,
    val legalReference: String,
    val dateText: String,
    val imageBase64: String? = null // Guarda la foto en formato texto (Base64)
)