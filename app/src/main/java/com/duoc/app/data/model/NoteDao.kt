package com.duoc.app.data.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes ORDER BY id DESC")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE isSynced = 0")
    suspend fun getUnsyncedNotes(): List<NoteEntity>

    @Query("SELECT * FROM notes WHERE remoteId = :remoteId LIMIT 1")
    suspend fun getByRemoteId(remoteId: String): NoteEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity): Long

    @Update
    suspend fun updateNote(note: NoteEntity)

    @Delete
    suspend fun deleteNote(note: NoteEntity)

    @Query("DELETE FROM notes WHERE isSynced = 1")
    suspend fun deleteAllSynced()
}