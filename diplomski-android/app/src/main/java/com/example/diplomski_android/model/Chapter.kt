package com.example.diplomski_android.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "chapters")
data class Chapter(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var course_id: Long? = null,
    var name: String? = "",
    var level: Int? = 0
){
    @Ignore
    var lessons: List<Lesson>? = null

    override fun toString(): String {
        return name!!
    }
}