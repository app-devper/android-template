package com.devper.template.common.db

import androidx.lifecycle.LiveData
import androidx.room.*

import com.devper.template.common.model.User

@Dao
interface UserDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(item: User)

    @Query("SELECT * FROM users")
    fun getUser(): LiveData<List<User>>

    @Query("SELECT * FROM users LIMIT 1")
    fun getFirstUser(): User?

    @Query("SELECT * FROM users WHERE username = :username")
    fun getUserByUsername(username: String): LiveData<User>

    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    fun getUserById(id: String): User?

    @Query("DELETE FROM users")
    fun clearUser()
}