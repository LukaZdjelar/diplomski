package com.example.diplomski_android.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var name: String? = "",
    var email: String? ="",
    var password: String? =""
){
}