package com.devper.template.data.database

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.devper.template.domain.model.member.Member

@Dao
interface MemberDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(members: List<Member>)

    @Query("SELECT * FROM members")
    fun getMemberFactory(): DataSource.Factory<Int, Member>

    @Query("SELECT * FROM members")
    fun getMember(): LiveData<List<Member>>

    @Query("DELETE FROM members WHERE id = :id")
    fun deleteMemberById(id: String)

}