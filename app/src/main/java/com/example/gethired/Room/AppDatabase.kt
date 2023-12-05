package com.example.gethired.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [MessageRoomDto::class], version = 4)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun messageRoomDao(): MessageRoomDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "message_database"
                )
                    .addMigrations(MIGRATION_3_4)
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
        val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("PRAGMA foreign_keys=off;")
                database.execSQL("BEGIN TRANSACTION;")
                database.execSQL("CREATE TABLE message_table_new (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                        "senderId LONG NOT NULL, " +
                        "receiverId LONG NOT NULL, " +
                        "content TEXT, " +
                        "timeStamp TEXT, " +  // Change the type to TEXT to allow null
                        "seen INTEGER NOT NULL DEFAULT 0);")
                database.execSQL("INSERT INTO message_table_new (id, senderId, receiverId, content, timeStamp, seen) " +
                        "SELECT id, senderId, receiverId, content, timeStamp, seen FROM message_table;")
                database.execSQL("DROP TABLE message_table;")
                database.execSQL("ALTER TABLE message_table_new RENAME TO message_table;")
                database.execSQL("COMMIT;")
                database.execSQL("PRAGMA foreign_keys=on;")
            }
        }

    }



}