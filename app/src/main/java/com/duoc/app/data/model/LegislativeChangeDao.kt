package com.duoc.app.data.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LegislativeChangeDao {
    @Query("SELECT * FROM legislative_changes")
    fun getAllChanges(): Flow<List<LegislativeChangeEntity>>

    @Query("SELECT * FROM legislative_changes WHERE id = :id LIMIT 1")
    suspend fun getChangeById(id: String): LegislativeChangeEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(changes: List<LegislativeChangeEntity>)

    @Query("DELETE FROM legislative_changes")
    suspend fun clearAll()
}