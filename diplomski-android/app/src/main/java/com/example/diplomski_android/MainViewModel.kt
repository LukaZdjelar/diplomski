package com.example.diplomski_android

import android.widget.ArrayAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diplomski_android.data.repository.*
import com.example.diplomski_android.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
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

    val coursesStateFlow = MutableStateFlow(listOf(Course()))
    val chaptersStateFlow = MutableStateFlow(listOf(Chapter()))
    val lessonsStateFlow = MutableStateFlow(listOf(Lesson()))
    val tasksStateFlow = MutableStateFlow(listOf(Task()))
    val languagesStateFlow = MutableStateFlow(listOf(Language()))

    fun getCourses(){
        viewModelScope.launch {
            courseRepository.getAllFlow().collect {
                coursesStateFlow.value = it
            }
        }
    }
    suspend fun insertCourse(course: Course){
        courseRepository.insert(course)
    }
    fun getCourseById(id: Long): Course {
        return courseRepository.getById(id)
    }

    fun getChaptersByCourseFlow(id: Long){
        viewModelScope.launch {
            chapterRepository.getByCourseFlow(id).collect {
                chaptersStateFlow.value = it
            }
        }
    }
    fun getChaptersByCourse(id: Long): List<Chapter>{
        return chapterRepository.getByCourse(id)
    }
    fun getChapterById(id: Long): Chapter{
        return chapterRepository.getById(id)
    }
    suspend fun insertChapter(chapter: Chapter){
        chapterRepository.insert(chapter)
    }

    fun getLessonsByChapterFlow(id: Long){
        viewModelScope.launch {
            lessonRepository.getByChapterFlow(id).collect {
                lessonsStateFlow.value = it
            }
        }
    }
    fun getLessonsByChapters(id: Long): List<Lesson>{
        return lessonRepository.getByChapter(id)
    }
    fun getLessonsById(id: Long): Lesson{
        return lessonRepository.getById(id)
    }
    suspend fun insertLesson(lesson: Lesson){
        lessonRepository.insert(lesson)
    }

    fun getTasksByLessonFlow(id: Long){
        viewModelScope.launch {
            taskRepository.getByLesson(id).collect {
                tasksStateFlow.value = it
            }
        }
    }
    suspend fun insertTask(task: Task){
        taskRepository.insert(task)
    }

    fun getLanguages() {
        viewModelScope.launch {
            languageRepository.getAllFlow().collect {
                languagesStateFlow.value = it
            }
        }
    }
    fun getLanguageById(id: Long): Language{
        return languageRepository.getById(id)
    }

//    private val _course = MutableStateFlow(Course())
//    val course : StateFlow<Course> = _course
//    fun setCourse(selectedCourse: Course){
//        viewModelScope.launch {
//            selectedCourse.chapters = getChaptersByCourse(selectedCourse.id!!)
//            selectedCourse.chapters!!.forEach { chapter ->
//                chapter.lessons = getLessonsByChapter(chapter.id!!)
//                chapter.lessons!!.forEach { lesson ->
//                    lesson.tasks = getTasksByLesson(lesson.id!!)
//                }
//            }
//        }
//        _course.value = selectedCourse
//    }

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
        if (nextTaskNumber < tasksStateFlow.value.size){
            setTaskNumber(nextTaskNumber)
            setTask(tasksStateFlow.value[taskNumber.value!!])
        }else{
            setCompleted(true)
        }
    }

    //INSERT COURSE
    private val _newCourse = MutableLiveData(Course())
    val newCourse : LiveData<Course> = _newCourse
    fun setNewCourse(nc: Course){
        _newCourse.value = nc
        if (nc.id != null){
            CoroutineScope(Dispatchers.IO).launch {
                newCourse.value?.local_language = getLanguageById(nc.local_language_id!!)
                newCourse.value?.foreign_language = getLanguageById(nc.foreign_language_id!!)
            }
        }
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
        if (nc.id != null){
            CoroutineScope(Dispatchers.IO).launch{
                newChapter.value?.course = getCourseById(nc.course_id!!)
            }
        }
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

    //INSERT LESSON
    private val _newLesson = MutableLiveData(Lesson())
    val newLesson : LiveData<Lesson> = _newLesson
    fun setNewLesson(nl: Lesson){
        _newLesson.value = nl
        if (nl.id != null){
            CoroutineScope(Dispatchers.IO).launch{
                newLesson.value?.chapter = getChapterById(nl.chapter_id!!)
                newLesson.value?.course = getCourseById(newLesson.value?.chapter!!.course_id!!)
            }
        }
    }

    fun onLessonChapterItemSelected(adapter: ArrayAdapter<Chapter>, position: Int){
        val chapter = adapter.getItem(position)!!
        newLesson.value?.chapter_id = chapter.id
    }
    fun onLessonTypeItemSelected(adapter: ArrayAdapter<String>, position: Int){
        val type = adapter.getItem(position)!!
        newLesson.value?.lesson_type = type
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
        if (nt.id != null){
            CoroutineScope(Dispatchers.IO).launch{
                newTask.value?.lesson = getLessonsById(nt.lesson_id!!)
                newTask.value?.chapter = getChapterById(newTask.value?.lesson!!.chapter_id!!)
                newTask.value?.course = getCourseById(newTask.value?.chapter!!.course_id!!)
            }
        }
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