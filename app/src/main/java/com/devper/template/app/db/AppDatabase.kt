package com.devper.template.app.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.devper.template.app.db.dao.MemberDao
import com.devper.template.app.db.dao.UserDao
import com.devper.template.app.model.User
import com.devper.template.member.model.Member

@Database(
    entities = [Member::class, User::class], version = 1, exportSchema = false
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