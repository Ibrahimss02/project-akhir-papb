package com.papb.projectakhirpapb.roomdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {

    @Insert
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Query("SELECT * from user_table LIMIT 1")
    suspend fun getLastCurrentUser()

    @Query("DELETE from user_table")
    suspend fun clearAllUser()
}