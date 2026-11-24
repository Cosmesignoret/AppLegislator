package com.duoc.app.data.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.duoc.app.data.model.DocumentDao
import com.duoc.app.data.model.DocumentEntity
import com.duoc.app.data.model.LegislativeChangeDao
import com.duoc.app.data.model.LegislativeChangeEntity
import com.duoc.app.data.model.NoteDao
import com.duoc.app.data.model.NoteEntity

@Database(
    entities = [
        NoteEntity::class,
        DocumentEntity::class,
        LegislativeChangeEntity::class
    ],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao
    abstract fun documentDao(): DocumentDao
    abstract fun legislativeChangeDao(): LegislativeChangeDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    "CREATE TABLE IF NOT EXISTS `documents` (" +
                            "`id` TEXT NOT NULL, " +
                            "`title` TEXT NOT NULL, " +
                            "`category` TEXT NOT NULL, " +
                            "`content` TEXT NOT NULL, " +
                            "PRIMARY KEY(`id`))"
                )
            }
        }

        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    "CREATE TABLE IF NOT EXISTS `legislative_changes` (" +
                            "`id` TEXT NOT NULL, " +
                            "`title` TEXT NOT NULL, " +
                            "`date` TEXT NOT NULL, " +
                            "`summary` TEXT NOT NULL, " +
                            "`currentSegmentsJson` TEXT NOT NULL, " +
                            "`previousSegmentsJson` TEXT NOT NULL, " +
                            "PRIMARY KEY(`id`))"
                )
            }
        }

        private val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE `notes` ADD COLUMN `remoteId` TEXT")
                db.execSQL("ALTER TABLE `notes` ADD COLUMN `isSynced` INTEGER NOT NULL DEFAULT 0")
            }
        }

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "AppLegislator_database"
                )
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}