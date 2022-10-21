package com.example.diplomski_android.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var lessonId: Long,
    var question: String,
    var answer: String
)