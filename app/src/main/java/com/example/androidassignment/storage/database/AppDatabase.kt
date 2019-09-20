package com.example.androidassignment.storage.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.androidassignment.storage.dao.UserInfoDao
import com.example.androidassignment.storage.entity.UserInfoEntity


@Database(entities = arrayOf(UserInfoEntity::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userInfoDao(): UserInfoDao

}
