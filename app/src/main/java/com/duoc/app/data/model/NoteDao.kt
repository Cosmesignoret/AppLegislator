package com.duoc.app.data.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * Consultas directas a la base de datos con Room.
 */
@Dao
interface NoteDao {

    @Query("SELECT * FROM notes ORDER BY id DESC")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity)

    @Delete
    suspend fun deleteNote(note: NoteEntity)
}
