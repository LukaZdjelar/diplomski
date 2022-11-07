package com.example.diplomski_android.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var lesson_id: Long? = null,
    var question: String? = "",
    var answer: String? = ""
){
    @Ignore
    var course: Course? = null
    @Ignore
    var chapter: Chapter? = null
    @Ignore
    var lesson: Lesson? = null
}