package com.papb.projectakhirpapb.roomdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey
    val id : String = "",

    @ColumnInfo(name = "username")
    val username : String? = null,

    @ColumnInfo(name = "favorites_list")
    val favoriteGame : ArrayList<String>? = arrayListOf()
)
