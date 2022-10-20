package com.example.diplomski_android.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lessons")
data class Lesson(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var chapterId: Long? = 0,
    var lessonStatus: String? = "",
    var lessonType: String = ""
)