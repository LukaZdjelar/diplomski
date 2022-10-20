package com.example.diplomski_android.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "courses")
data class Course(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var name: String = "",
    var local_language: String? = "",
    var foreign_language: String? = ""
)