package com.example.diplomski_android.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chapters")
data class Chapter(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var courseId: Long = 0,
    var name: String = "",
//    var isLocked: Boolean = true,
    var level: Int = 0
)