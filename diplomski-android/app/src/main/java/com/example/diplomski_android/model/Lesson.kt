package com.example.diplomski_android.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lessons")
data class Lesson(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var chapterId: Long,
    var lessonStatus: String,
    var lessonType: String
)