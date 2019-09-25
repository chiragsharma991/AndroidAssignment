package com.example.androidassignment.storage.dao

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.androidassignment.storage.entity.UserInfoEntity


@Dao
interface UserInfoDao {

    @get:Query("SELECT * FROM userinfo") // give []  if no data found
    val  all: List<UserInfoEntity>

    @Insert
    fun insert(task: UserInfoEntity)

    @Delete
    fun delete(task: UserInfoEntity)

    @Update
    fun update(task: UserInfoEntity)


    @Query("DELETE FROM userinfo")
    fun drop()

    @RawQuery
    fun clearPrimaryKey(query: SupportSQLiteQuery): Int  //We can return int status like it used to return with database.delete()


    /*   @Query("SELECT * FROM User WHERE " +
               "addr_home_lat BETWEEN :lat1 AND :lat2" +
               " AND addr_home_lng BETWEEN :lng1 AND :lng2")
       fun findInRange(lat1: Long, lat2: Long, lng1: Long, lng2: Long): List<User>*/


}