package com.example.diplomski_android.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chapters")
data class Chapter(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var courseId: Long,
    var name: String,
//    var isLocked: Boolean,
    var level: Int
)