package com.devper.template.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.devper.template.data.remote.user.UserData

@Database(
    entities = [UserData::class], version = 3, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun user(): UserDao

    companion object {
        fun create(context: Context, useInMemory: Boolean): AppDatabase {
            val databaseBuilder = if (useInMemory) {
                Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            } else {
                Room.databaseBuilder(context, AppDatabase::class.java, "app.db")
            }
            return databaseBuilder.run{
                fallbackToDestructiveMigration()
                build()
            }
        }
    }
}