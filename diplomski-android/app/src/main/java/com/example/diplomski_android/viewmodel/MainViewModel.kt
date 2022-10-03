package com.example.diplomski_android.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.diplomski_android.model.Course
import com.example.diplomski_android.model.Lesson

class MainViewModel:ViewModel() {
    private val _course = MutableLiveData<Course>()
    val course : LiveData<Course> = _course

    fun setCourse(selectedCourse: Course){
        _course.value = selectedCourse
    }

    private val _lesson = MutableLiveData<Lesson>()
    val lesson : LiveData<Lesson> = _lesson

    fun setLesson(selectedLesson: Lesson){
        _lesson.value = selectedLesson
    }
}