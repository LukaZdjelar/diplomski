package com.example.diplomski_android.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "lessons")
data class Lesson(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var chapter_id: Long? = null,
    var lesson_status: String? = "",
    var lesson_type: String? = ""
){
    @Ignore
    var tasks: List<Task>? = null
}