package com.example.diplomski_android.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.diplomski_android.data.dao.*
import com.example.diplomski_android.model.*

@Database(entities = [Course::class, Chapter::class, Lesson::class, Task::class, Language::class], version = 5, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

//    @Binds
    abstract fun courseDao(): CourseDao
//    @Binds
    abstract fun chapterDao(): ChapterDao
//    @Binds
    abstract fun lessonDao(): LessonDao
//    @Binds
    abstract fun taskDao(): TaskDao
//    @Binds
    abstract fun languageDao(): LanguageDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

//        @Provides
        fun getDatabase(context: Context): AppDatabase{
            val tempInstance = INSTANCE
            if (tempInstance!=null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}