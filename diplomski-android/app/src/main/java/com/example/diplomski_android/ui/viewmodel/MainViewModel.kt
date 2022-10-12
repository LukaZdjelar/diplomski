package com.example.diplomski_android.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.diplomski_android.data.retrofit.RetrofitInstance
import com.example.diplomski_android.data.service.TaskService
import com.example.diplomski_android.model.Course
import com.example.diplomski_android.model.Lesson
import com.example.diplomski_android.model.Task
import kotlinx.coroutines.runBlocking

class MainViewModel:ViewModel() {
    private val taskService: TaskService = TaskService()

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

    private val _task = MutableLiveData<Task>()
    val task : LiveData<Task> = _task

    fun setTask(nextTask: Task){
        _task.value = nextTask
    }

    val answer = MutableLiveData<String>()
    private fun resetAnswer(){
        answer.value = ""
    }

    private val _taskNumber = MutableLiveData(0)
    val taskNumber : LiveData<Int> = _taskNumber

    fun setTaskNumber(nextTaskNumber: Int){
        _taskNumber.value = nextTaskNumber
    }

    fun onAnswerButtonClick(){
        runBlocking {
            taskService.checkAnswer(task.value?.id!!, answer.value!!)
        }
        resetAnswer()
        setTaskNumber(taskNumber.value!! +1)
        setTask(lesson.value?.tasks!![taskNumber.value!!])
//        it.hideKeyboard()
    }
}