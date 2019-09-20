package com.example.androidassignment.storage.database

import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.room.Room
import android.content.Context


class DatabaseClient
private constructor(private val mCtx: Context) {


    var mInstance: DatabaseClient? = null

    @Synchronized
    fun getInstance(mCtx: Context): DatabaseClient {
        if (mInstance == null) {
            mInstance = DatabaseClient(mCtx)
        }
        return mInstance as DatabaseClient
    }


    //our app database object
    val appDatabase: AppDatabase
    val inMemoryAppDatabase: AppDatabase

    init {

        appDatabase = Room.databaseBuilder(mCtx, AppDatabase::class.java, "info.db").build()
        // appDatabase = Room.databaseBuilder(mCtx, AppDatabase::class.java, "chinook.db").addMigrations(AppDatabase.MIGRATION_1_2).build()
        inMemoryAppDatabase = Room.inMemoryDatabaseBuilder(mCtx, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()


    }


    fun getappDatabase(): AppDatabase {
        return appDatabase
    }

    fun getmemoryDatabase(): AppDatabase {
        return inMemoryAppDatabase
    }


    fun getInMemoryDatabase(): SupportSQLiteDatabase {
        return inMemoryAppDatabase.openHelper.writableDatabase
    }


}
