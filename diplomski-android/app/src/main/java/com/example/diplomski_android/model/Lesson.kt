package com.example.diplomski_android.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.firebase.firestore.Exclude

@Entity(tableName = "lessons")
data class Lesson(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var chapter_id: Long? = null,
    var lesson_type: String? = ""
){
    @Ignore
    @get:Exclude
    var tasks: List<Task>? = null
    @Ignore
    @get:Exclude
    var course: Course? = null
    @Ignore
    @get:Exclude
    var chapter: Chapter? = null
    @Ignore
    @get:Exclude
    var isCompleted: Boolean? = null

    override fun toString(): String {
        return lesson_type!!
    }
}