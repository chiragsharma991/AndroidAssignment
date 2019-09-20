package com.example.androidassignment.storage.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "userinfo")
data class UserInfoEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "serial_number") var serial_number: Long?, // sec id: 1->1.1,1.2
    @ColumnInfo(name = "title") var title: String

    //@Embedded(prefix = "test") var test_answer: Test? =null // answer : answer

) : Serializable