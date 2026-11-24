package com.duoc.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "documents")
data class DocumentEntity(
    @PrimaryKey val id: String,
    val title: String,
    val category: String,
    val content: String
)