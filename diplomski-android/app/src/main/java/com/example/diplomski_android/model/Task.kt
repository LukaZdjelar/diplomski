package com.example.diplomski_android.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.firebase.firestore.Exclude

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var lesson_id: Long? = null,
    var question: String? = "",
    var answer: String? = ""
){
    @Ignore
    @get:Exclude
    var course: Course? = null
    @Ignore
    @get:Exclude
    var chapter: Chapter? = null
    @Ignore
    @get:Exclude
    var lesson: Lesson? = null
}