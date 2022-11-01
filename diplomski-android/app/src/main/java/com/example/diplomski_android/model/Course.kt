package com.example.diplomski_android.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "courses")
data class Course(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var name: String? = "",
    var local_language_id: Long? = null,
    var foreign_language_id: Long? = null
){
    @Ignore
    var chapters: List<Chapter>? = null
}