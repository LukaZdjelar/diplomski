package com.example.diplomski_android.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "chapters")
data class Chapter(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var course_id: Long,
    var name: String,
//    var isLocked: Boolean,
    var level: Int
){
    @Ignore
    var lessons: List<Lesson>? = null
}