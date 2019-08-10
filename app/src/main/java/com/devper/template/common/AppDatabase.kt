package com.devper.template.common

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.devper.template.common.db.MemberDao
import com.devper.template.common.db.UserDao
import com.devper.template.common.model.User
import com.devper.template.member.model.Member

@Database(
    entities = [Member::class, User::class], version = 3, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun member(): MemberDao

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
                allowMainThreadQueries()
                build()
            }
        }
    }
}