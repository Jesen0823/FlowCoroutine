package com.jesen.pagingbookstore.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class VideoEntity (
    @PrimaryKey
    val id: Int,
    val name: String,
    val page: Int = 0,
    val icon:String
)