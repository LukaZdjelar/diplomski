package com.example.diplomski_android.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "languages")
data class Language(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var name: String
){
    override fun toString(): String {
        return name
    }
}