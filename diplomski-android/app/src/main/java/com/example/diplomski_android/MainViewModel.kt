package com.example.diplomski_android

import android.widget.ArrayAdapter
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
    suspend fun insertLesson(lesson: Lesson){
        lessonRepository.insert(lesson)
    }
    fun getTasksByLesson(id: Long): List<Task>{
        return taskRepository.getByLesson(id)
    }
    suspend fun insertTask(task: Task){
        taskRepository.insert(task)
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

        resetAnswer()
        val nextTaskNumber = taskNumber.value!! + 1
        if (nextTaskNumber < tasks.value!!.size){
            setTaskNumber(nextTaskNumber)
            setTask(tasks.value!![taskNumber.value!!])
        }else{
            setCompleted(true)
        }
    }

    //INSERT COURSE
    private val _newCourse = MutableLiveData(Course())
    val newCourse : LiveData<Course> = _newCourse
    fun setNewCourse(nc: Course){
        _newCourse.value = nc
    }

    fun onLocalLanguageItemSelected(adapter: ArrayAdapter<Language>, position: Int){
        val localLanguage = adapter.getItem(position)!!
        newCourse.value?.local_language_id = localLanguage.id
    }
    fun onForeignLanguageItemSelected(adapter: ArrayAdapter<Language>, position: Int){
        val foreignLanguage = adapter.getItem(position)!!
        newCourse.value?.foreign_language_id = foreignLanguage.id
    }
    fun onInsertCourseButtonClick(){
        CoroutineScope(Dispatchers.IO).launch {
            insertCourse(newCourse.value!!)
        }
    }

    //INSERT CHAPTER
    private val _newChapter = MutableLiveData(Chapter())
    val newChapter : LiveData<Chapter> = _newChapter
    fun setNewChapter(nc: Chapter){
        _newChapter.value = nc
    }

    fun onChapterCourseItemSelected(adapter: ArrayAdapter<Course>, position: Int){
        val course = adapter.getItem(position)!!
        newChapter.value?.course_id = course.id
    }
    fun onInsertChapterButtonClick(){
        CoroutineScope(Dispatchers.IO).launch {
            insertChapter(newChapter.value!!)
        }
    }

    //INSERT CHAPTER
    private val _newLesson = MutableLiveData(Lesson())
    val newLesson : LiveData<Lesson> = _newLesson
    fun setNewLesson(nl: Lesson){
        _newLesson.value = nl
    }

    fun onLessonChapterItemSelected(adapter: ArrayAdapter<Chapter>, position: Int){
        val chapter = adapter.getItem(position)!!
        newLesson.value?.chapter_id = chapter.id
    }
    fun onInsertLessonButtonClick(){
        CoroutineScope(Dispatchers.IO).launch {
            insertLesson(newLesson.value!!)
        }
    }

    //INSERT TASK
    private val _newTask = MutableLiveData(Task())
    val newTask : LiveData<Task> = _newTask
    fun setNewTask(nt: Task){
        _newTask.value = nt
    }

    fun onTaskLessonItemSelected(adapter: ArrayAdapter<Lesson>, position: Int){
        val lesson = adapter.getItem(position)!!
        newTask.value?.lesson_id = lesson.id
    }
    fun onInsertTaskButtonClick(){
        CoroutineScope(Dispatchers.IO).launch {
            insertTask(newTask.value!!)
        }
    }
}