package com.duoc.app.data.remote.dto

data class CreateNoteRequest(
    val title: String,
    val content: String,
    val legalReference: String,
    val dateText: String,
    val imageBase64: String? = null
)

data class NoteDto(
    val id: String,
    val title: String,
    val content: String,
    val legalReference: String,
    val dateText: String,
    val imageBase64: String? = null
)