package com.duoc.app.data.remote.api

import com.duoc.app.data.remote.dto.CreateNoteRequest
import com.duoc.app.data.remote.dto.NoteDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface NoteApi {
    @GET("notes")
    suspend fun getNotes(): List<NoteDto>

    @POST("notes")
    suspend fun createNote(@Body request: CreateNoteRequest): NoteDto

    @DELETE("notes/{id}")
    suspend fun deleteNote(@Path("id") id: String)
}