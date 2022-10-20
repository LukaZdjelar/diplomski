package com.example.diplomski_android.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.diplomski_android.data.repository.CourseRepository
import com.example.diplomski_android.model.*

class MainViewModel:ViewModel() {

    //TODO:svi entiteti
    private val _courses = MutableLiveData<List<Course>>()
    val courses : LiveData<List<Course>> = _courses
    fun setCourses(c: List<Course>){
        _courses.value = c
    }

    private val _chapters = MutableLiveData<List<Chapter>>()
    val chapters : LiveData<List<Chapter>> = _chapters
    fun setChapters(c: List<Chapter>){
        _chapters.value = c
    }

    private val _lessons = MutableLiveData<List<Lesson>>()
    val lessons : LiveData<List<Lesson>> = _lessons
    fun setLessons(l: List<Lesson>){
        _lessons.value = l
    }

    private val _tasks = MutableLiveData<List<Task>>()
    val tasks : LiveData<List<Task>> = _tasks
    fun setTasks(t: List<Task>){
        _tasks.value = t
    }

    private val _languages = MutableLiveData<List<Language>>()
    val languages : LiveData<List<Language>> = _languages
    fun setLanguages(l: List<Language>){
        _languages.value = l
    }
    ////

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

    private val _task = MutableLiveData<Task?>()
    val task : LiveData<Task?> = _task

    fun setTask(nextTask: Task?){
        _task.value = nextTask
    }

    val answer = MutableLiveData("")
    private fun resetAnswer(){
        answer.value = ""
    }

    private val _taskNumber = MutableLiveData(0)
    val taskNumber : LiveData<Int> = _taskNumber

    fun setTaskNumber(nextTaskNumber: Int){
        _taskNumber.value = nextTaskNumber
    }

    private val _completed = MutableLiveData(false)
    val completed : LiveData<Boolean> = _completed

    fun setCompleted(isCompleted: Boolean){
        _completed.value = isCompleted
    }

    fun onAnswerButtonClick(){
//        runBlocking {
//            taskService.checkAnswer(task.value?.id!!, answer.value!!)
//        }
//        resetAnswer()
//
//        val nextTaskNumber = taskNumber.value!! + 1
//        if (nextTaskNumber < lesson.value?.tasks!!.size){
//            setTaskNumber(nextTaskNumber)
//            setTask(lesson.value?.tasks!![taskNumber.value!!])
//        }else{
//            setCompleted(true)
//        }
    }
}