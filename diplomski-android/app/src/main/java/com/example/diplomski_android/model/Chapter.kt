package com.example.diplomski_android.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.firebase.firestore.Exclude

@Entity(tableName = "chapters")
data class Chapter(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var course_id: Long? = null,
    var name: String? = "",
    var difficulty: String? = ""
){
    @Ignore
    @get:Exclude
    var lessons: List<Lesson>? = null
    @Ignore
    @get:Exclude
    var course: Course? = null
    @Ignore
    @get:Exclude
    var totalLessons: Int? = 0
    @Ignore
    @get:Exclude
    var completedLessons: Int? = 0

    override fun toString(): String {
        return name!!
    }
}