package com.example.diplomski_android.data.di

import android.app.Application
import android.content.Context
import com.example.diplomski_android.MyApplication
import com.example.diplomski_android.data.dao.ChapterDao
import com.example.diplomski_android.data.dao.CourseDao
import com.example.diplomski_android.data.dao.LessonDao
import com.example.diplomski_android.data.dao.TaskDao
import com.example.diplomski_android.data.database.AppDatabase
import com.example.diplomski_android.data.repository.ChapterRepository
import com.example.diplomski_android.data.repository.CourseRepository
import com.example.diplomski_android.data.repository.LessonRepository
import com.example.diplomski_android.data.repository.TaskRepository
import com.example.diplomski_android.data.repository.impl.ChapterRepositoryImpl
import com.example.diplomski_android.data.repository.impl.CourseRepositoryImpl
import com.example.diplomski_android.data.repository.impl.LessonRepositoryImpl
import com.example.diplomski_android.data.repository.impl.TaskRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContext(): Context {
        return MyApplication.applicationContext()
    }

    @Provides
    @Singleton
    fun provideDatabase(context: Application): AppDatabase{
        return AppDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideCourseDao(appDatabase: AppDatabase): CourseDao{
        return appDatabase.courseDao()
    }

    @Provides
    @Singleton
    fun provideCourseRepository(courseDao: CourseDao): CourseRepository{
        return CourseRepositoryImpl(courseDao)
    }

    @Provides
    @Singleton
    fun provideChapterDao(appDatabase: AppDatabase): ChapterDao{
        return appDatabase.chapterDao()
    }

    @Provides
    @Singleton
    fun provideChapterRepository(chapterDao: ChapterDao): ChapterRepository{
        return ChapterRepositoryImpl(chapterDao)
    }

    @Provides
    @Singleton
    fun provideLessonDao(appDatabase: AppDatabase): LessonDao{
        return appDatabase.lessonDao()
    }

    @Provides
    @Singleton
    fun provideLessonRepository(lessonDao: LessonDao): LessonRepository{
        return LessonRepositoryImpl(lessonDao)
    }

    @Provides
    @Singleton
    fun provideTaskDao(appDatabase: AppDatabase): TaskDao{
        return appDatabase.taskDao()
    }

    @Provides
    @Singleton
    fun provideTaskRepository(taskDao: TaskDao): TaskRepository{
        return TaskRepositoryImpl(taskDao)
    }
}