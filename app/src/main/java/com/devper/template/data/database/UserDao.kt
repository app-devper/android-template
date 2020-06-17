package com.devper.template.data.database

import androidx.room.*
import com.devper.template.data.remote.user.UserData

@Dao
interface UserDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(item: UserData)

    @Query("SELECT * FROM users")
    suspend fun getUser(): List<UserData>

    @Query("SELECT * FROM users LIMIT 1")
    suspend fun getFirstUser(): UserData?

    @Query("SELECT * FROM users WHERE username = :username")
    suspend fun getUserByUsername(username: String): UserData

    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    suspend fun getUserById(id: String): UserData?

    @Query("DELETE FROM users")
    suspend fun clearUser()
}