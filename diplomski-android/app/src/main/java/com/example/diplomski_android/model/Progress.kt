package com.example.diplomski_android.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "progress")
data class Progress(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var user_id: Long? = null,
    var lesson_id: Long? = null,
)