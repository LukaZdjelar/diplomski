package com.example.diplomski_android

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.diplomski_android.data.repository.*
import com.example.diplomski_android.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val courseRepository: CourseRepository,
    private val chapterRepository: ChapterRepository,
    private val lessonRepository: LessonRepository,
    private val taskRepository: TaskRepository,
    private val languageRepository: LanguageRepository
):ViewModel() {

    fun getCourses(): List<Course> {
        return courseRepository.getAll()
    }
    suspend fun insertCourse(course: Course){
        courseRepository.insert(course)
    }
    fun getChaptersByCourse(id: Long): List<Chapter>{
        return chapterRepository.getByCourse(id)
    }
    suspend fun insertChapter(chapter: Chapter){
        chapterRepository.insert(chapter)
    }
    fun getLessonsByChapter(id: Long): List<Lesson>{
        return lessonRepository.getByChapter(id)
    }
    fun getTasksByLesson(id: Long): List<Task>{
        return taskRepository.getByLesson(id)
    }
    fun getLanguages(): List<Language>{
        return languageRepository.getAll()
    }

    private val _course = MutableLiveData<Course>()
    val course : LiveData<Course> = _course

    fun setCourse(selectedCourse: Course){
        CoroutineScope(Dispatchers.IO).launch {
            selectedCourse.chapters = getChaptersByCourse(selectedCourse.id!!)
            selectedCourse.chapters!!.forEach { chapter ->
                chapter.lessons = getLessonsByChapter(chapter.id!!)
                chapter.lessons!!.forEach { lesson ->
                    lesson.tasks = getTasksByLesson(lesson.id!!)
                }
            }
//            for(chapter in selectedCourse.chapters!!){
//                chapter.lessons = getLessonsByChapter(chapter.id)
//                for (lesson in chapter.lessons!!){
//                    lesson.tasks = getTasksByLesson(lesson.id)
//                }
//            }
        }
        _course.value = selectedCourse
    }

    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> = _tasks
    fun setTasks(setTasks: List<Task>){
        _tasks.value = setTasks
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
        val nextTaskNumber = taskNumber.value!! + 1
        if (nextTaskNumber < tasks.value!!.size){
            setTaskNumber(nextTaskNumber)
            setTask(tasks.value!![taskNumber.value!!])
        }else{
            setCompleted(true)
        }
    }

    private val _languages = MutableLiveData<List<Language>>()
    val languages: LiveData<List<Language>> = _languages
    fun setLanguages(setLanguages: List<Language>){
        _languages.value = setLanguages
    }

    private val _createCourse = MutableLiveData<Course>()
    val createCourse : LiveData<Course> = _createCourse
    fun setCreateCourse(createdData: Course){
        _createCourse.value = createdData
    }
}