package com.example.diplomski_android.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.firebase.firestore.Exclude

@Entity(tableName = "courses")
data class Course(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var name: String? = "",
    var local_language_id: Long? = null,
    var foreign_language_id: Long? = null
){
    @Ignore
    @get:Exclude
    var chapters: List<Chapter>? = null
    @Ignore
    @get:Exclude
    var localLanguage: Language? = null
    @Ignore
    @get:Exclude
    var foreignLanguage: Language? = null

    override fun toString(): String {
        return name.toString()
    }
}