package com.papb.projectakhirpapb.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {

    abstract val userDatabaseDao: UserDao

    companion object {
        @Volatile
        private var INSTANCE: com.papb.projectakhirpapb.roomdb.Database? = null

        fun getInstance(context: Context): com.papb.projectakhirpapb.roomdb.Database {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        com.papb.projectakhirpapb.roomdb.Database::class.java,
                        "user_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}