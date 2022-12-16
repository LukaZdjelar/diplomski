package com.example.diplomski_android

import android.widget.ArrayAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diplomski_android.data.repository.firestore.*
import com.example.diplomski_android.data.repository.room.*
import com.example.diplomski_android.model.*
import com.google.firebase.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val courseRepository: CourseRepository,
    private val courseFirestore: CourseFirestore,
    private val chapterRepository: ChapterRepository,
    private val chapterFirestore: ChapterFirestore,
    private val lessonRepository: LessonRepository,
    private val lessonFirebase: LessonFirebase,
    private val taskRepository: TaskRepository,
    private val taskFirestore: TaskFirestore,
    private val languageRepository: LanguageRepository,
    private val languageFirestore: LanguageFirestore,
    private val progressRepository: ProgressRepository,
    private val progressFirestore: ProgressFirestore,
    private val userRepository: UserRepository,
    private val userFirestore: UserFirestore,
    private val authFirestore: AuthFirestore,
):ViewModel() {

    val coursesStateFlow = MutableStateFlow(listOf(Course()))
    val chaptersStateFlow = MutableStateFlow(listOf(Chapter()))
    val lessonsStateFlow = MutableStateFlow(listOf(Lesson()))
    val tasksStateFlow = MutableStateFlow(listOf(Task()))
    val languagesStateFlow = MutableStateFlow(listOf(Language()))

    private val _currentCourse = MutableLiveData<Course?>()
    val currentCourse : LiveData<Course?> = _currentCourse
    fun setCurrentCourse(cc: Course?){
        _currentCourse.value = cc
    }

    private val _currentChapter = MutableLiveData<Chapter?>()
    val currentChapter : LiveData<Chapter?> = _currentChapter
    fun setCurrentChapter(cc: Chapter?){
        _currentChapter.value = cc
    }

    private val _currentLesson = MutableLiveData<Lesson?>()
    val currentLesson : LiveData<Lesson?> = _currentLesson
    fun setCurrentLesson(cl: Lesson?){
        _currentLesson.value = cl
    }

    fun getCourses(){
        viewModelScope.launch {
            courseRepository.getAllFlow().collect {
                coursesStateFlow.value = it
            }
        }
    }
    suspend fun insertCourse(course: Course){
        val id = courseRepository.insert(course)
        course.id = id
        courseFirestore.insert(course)
    }
    suspend fun insertAllCourses(courses: List<Course>){
        courseRepository.insertAll(courses)
    }
    fun getCourseById(id: Long): Course {
        return courseRepository.getById(id)
    }
    suspend fun deleteCourse(course: Course){
        courseRepository.delete(course)
        courseFirestore.delete(course)
    }
    suspend fun deleteAllCourses(){
        courseRepository.deleteAll()
    }
    suspend fun deleteCourseComplete(course: Course){
        getChaptersByCourse(course.id!!).forEach { chapter ->
            getLessonsByChapters(chapter.id!!).forEach { lesson ->
                deleteTasksByLesson(lesson.id!!)
            }
            deleteLesonsByChapter(chapter.id!!)
        }
        deleteChaptersByCourse(course.id!!)
        deleteCourse(course)
    }
    suspend fun coursesFirebaseSync(){
        deleteAllCourses()
        val result = courseFirestore.getAll()
        insertAllCourses(result)
    }

    fun getChaptersByCourseFlow(id: Long){
        chaptersStateFlow.value = emptyList()
        viewModelScope.launch {
            chapterRepository.getByCourseFlow(id).collect {
                it.forEach { chapter ->
                    withContext(Dispatchers.IO) {
                        chapter.totalLessons = countTotalLessons(chapter.id!!)
                        chapter.completedLessons = countCompletedLessons(chapter.id!!, user.value?.id!!)
                    }
                }
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
        val id = chapterRepository.insert(chapter)
        chapter.id = id
        chapterFirestore.insert(chapter)
    }
    suspend fun deleteChapter(chapter: Chapter){
        chapterRepository.delete(chapter)
        chapterFirestore.delete(chapter)
    }
    suspend fun deleteChaptersByCourse(courseId: Long){
        chapterRepository.deleteByCourse(courseId)
        chapterFirestore.deleteByCourse(courseId)
    }
    suspend fun deleteChapterComplete(chapter: Chapter){
        getLessonsByChapters(chapter.id!!).forEach { lesson ->
            deleteTasksByLesson(lesson.id!!)
        }
        deleteLesonsByChapter(chapter.id!!)
        deleteChapter(chapter)
    }
    fun countTotalLessons(chapterId: Long): Int {
        return chapterRepository.countTotalLessons(chapterId)
    }
    fun countCompletedLessons(chapterId: Long, userId: Long): Int {
        return chapterRepository.countCompletedLessons(chapterId, userId)
    }
    suspend fun chaptersFirebaseSync(){
        chapterRepository.deleteAll()
        val result = chapterFirestore.getAll()
        chapterRepository.insertAll(result)
    }

    fun getLessonsByChapterFlow(id: Long){
        //TODO: nece da promeni isCompleted na postojecem flow
        lessonsStateFlow.value = emptyList()
        viewModelScope.launch {
            lessonRepository.getByChapterFlow(id).collect {
                it.forEach { lesson ->
                    withContext(Dispatchers.IO) {
                        lesson.isCompleted = getLessonStatus(lesson.id!!, user.value?.id!!)
                    }
                }
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
        val id = lessonRepository.insert(lesson)
        lesson.id = id
        lessonFirebase.insert(lesson)
    }
    suspend fun deleteLesson(lesson: Lesson){
        lessonRepository.delete(lesson)
        lessonFirebase.delete(lesson)
    }
    suspend fun deleteLesonsByChapter(chapterId: Long){
        lessonRepository.deleteByChapter(chapterId)
        lessonFirebase.deleteByChapter(chapterId)
    }
    suspend fun deleteLessonComplete(lesson: Lesson){
        deleteTasksByLesson(lesson.id!!)
        deleteLesson(lesson)
    }
    fun getLessonStatus(lessonId: Long, userId: Long): Boolean{
        return lessonRepository.getLessonStatus(lessonId, userId)
    }
    suspend fun lessonsFirebaseSync(){
        lessonRepository.deleteAll()
        val result = lessonFirebase.getAll()
        lessonRepository.insertAll(result)
    }

    fun getTasksByLessonFlow(id: Long){
        viewModelScope.launch {
            taskRepository.getByLessonFlow(id).collect {
                tasksStateFlow.value = it
            }
        }
    }
    fun getTasksByLesson(id: Long): List<Task> {
        return taskRepository.getByLesson(id)
    }
    suspend fun insertTask(task: Task){
        val id = taskRepository.insert(task)
        task.id = id
        taskFirestore.insert(task)
    }
    suspend fun deleteTask(task: Task){
        taskRepository.delete(task)
        taskFirestore.delete(task)
    }
    suspend fun deleteTasksByLesson(lessonId: Long){
        taskRepository.deleteByLesson(lessonId)
        taskFirestore.deleteByLesson(lessonId)
    }
    fun countTasksByLesson(lessonId: Long): Int{
        return taskRepository.countByLesson(lessonId)
    }
    suspend fun tasksFirebaseSync(){
        taskRepository.deleteAll()
        val result = taskFirestore.getAll()
        taskRepository.insertAll(result)
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
    suspend fun insertLanguage(language: Language){
        val id = languageRepository.insert(language)
        language.id = id
        languageFirestore.insert(language)
    }
    suspend fun languagesFirebaseSync(){
        languageRepository.deleteAll()
        val result = languageFirestore.getAll()
        languageRepository.insertAll(result)
    }

    suspend fun insertProgress(progress: Progress){
        val id = progressRepository.insert(progress)
        progress.id = id
        progressFirestore.insert(progress)
    }
    fun findProgressByUserAndLessonBoolean(userId: Long, lessonId: Long): Boolean{
        return progressRepository.findByUserAndLessonBoolean(userId, lessonId)
    }
    suspend fun progressFirebaseSync(){
        progressRepository.deleteAll()
        val result = progressFirestore.getAll()
        progressRepository.insertAll(result)
    }

    suspend fun insertUser(user: User){
        val id = userRepository.insert(user)
        user.id = id
        userFirestore.insert(user)
    }
    suspend fun insertAllUsers(users: List<User>){
        userRepository.insertAll(users)
    }
    fun getUserByEmail(email: String): User{
        return userRepository.getByEmail(email)
    }
    fun getUserById(id: Long): User{
        return userRepository.getById(id)
    }
    suspend fun deleteAllUsers(){
        userRepository.deleteAll()
    }
    suspend fun usersFirebaseSync(){
        deleteAllUsers()
        val result = userFirestore.getAll()
        insertAllUsers(result)
    }

    private val _user = MutableLiveData(User())
    val user : LiveData<User> = _user
    fun setUser(value: User){
        _user.value = value
    }

    fun setUserById(id: Long){
        CoroutineScope(Dispatchers.IO).launch{
            val user = getUserById(id)
            viewModelScope.launch {
                _user.value = user
            }
        }
    }

    fun sharedPreferencesGetUserId(): Long{
        return userRepository.sharedPreferencesGetUserId()
    }
    fun sharedPreferencesPutUserId(id: Long){
        userRepository.sharedPreferencesPutUserId(id)
    }
    fun sharedPreferencesRemoveUserId(){
        userRepository.sharedPreferencesRemoveUserId()
    }

    private val _isAdmin = MutableLiveData(false)
    val isAdmin : LiveData<Boolean> = _isAdmin
    fun setIsAdmin(value: Boolean){
        _isAdmin.value = value
    }

    private val _task = MutableLiveData<Task?>()
    val task : LiveData<Task?> = _task
    fun setTask(nextTask: Task?){
        _task.value = nextTask
    }

    private val _tasks = MutableLiveData<List<Task?>>()
    val tasks : LiveData<List<Task?>> = _tasks
    fun setTasks(tasks: List<Task?>){
        _tasks.value = tasks
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

    private val _corect = MutableLiveData("Incorrect")
    val corect : LiveData<String> = _corect
    fun setCorect(isCorrect: String){
        _corect.value = isCorrect
    }

    private val _corectCounter = MutableLiveData(0)
    val corectCounter : LiveData<Int> = _corectCounter
    fun setCorectCounter(counter: Int){
        _corectCounter.value = counter
    }

    fun onAnswerButtonClick(){
        if (task.value?.answer!! == answer.value){
            setCorect("Correct")
            setCorectCounter(corectCounter.value!!.inc())
        }else{
            setCorect("Incorrect")
        }
    }

    fun onDialogNextButtonClick(){
        resetAnswer()
        val nextTaskNumber = taskNumber.value!! + 1
        if (nextTaskNumber < tasks.value?.size!!){
            setTaskNumber(nextTaskNumber)
            setTask(tasks.value!![taskNumber.value!!])
        }else{
            setCompleted(true)
        }
    }

    private val _grade = MutableLiveData("")
    val grade : LiveData<String> = _grade
    fun setGrade(newGrade: String){
        _grade.value = newGrade
    }

    private val _passed = MutableLiveData("")
    val passed : LiveData<String> = _passed
    fun setPassed(p: String){
        _passed.value = p
    }

    //INSERT PROGRESS
    suspend fun onLessonComplete(){
        val lessonId = currentLesson.value?.id!!

        withContext(Dispatchers.IO) {
            val numberOfTasks = countTasksByLesson(lessonId)
            viewModelScope.launch {
                val lessonGrade = corectCounter.value!!*1.0 / numberOfTasks
                val gradeString = "${(lessonGrade*100).toInt()}%"
                setGrade(gradeString)

                if(lessonGrade > 0.85){
                    withContext(Dispatchers.IO) {
                        if (!findProgressByUserAndLessonBoolean(user.value?.id!!, lessonId)){
                            insertProgress(Progress(null, user.value?.id!!, lessonId))
                        }
                        viewModelScope.launch {
                            setPassed("Passed")
                        }
                    }
                }
                else{
                    setPassed("Failed")
                }
            }
        }
    }

    fun onResultNextButtonClick(){
        setCorectCounter(0)
        setPassed("")
    }

    //INSERT COURSE
    private val _newCourse = MutableLiveData(Course())
    val newCourse : LiveData<Course> = _newCourse
    fun setNewCourse(nc: Course){
        _newCourse.value = nc
        if (nc.id != null){
            viewModelScope.launch {
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
        viewModelScope.launch {
            insertCourse(newCourse.value!!)
        }
    }

    //INSERT CHAPTER
    private val _newChapter = MutableLiveData(Chapter())
    val newChapter : LiveData<Chapter> = _newChapter
    fun setNewChapter(nc: Chapter){
        _newChapter.value = nc
        if (nc.id != null){
            viewModelScope.launch{
                newChapter.value?.course = getCourseById(nc.course_id!!)
            }
        }
    }

    fun onChapterCourseItemSelected(adapter: ArrayAdapter<Course>, position: Int){
        val course = adapter.getItem(position)!!
        newChapter.value?.course_id = course.id
    }
    fun onChapterDifficultyItemSelected(adapter: ArrayAdapter<String>, position: Int){
        val difficulty = adapter.getItem(position)!!
        newChapter.value?.difficulty = difficulty
    }
    fun onInsertChapterButtonClick(){
        viewModelScope.launch {
            insertChapter(newChapter.value!!)
        }
    }

    //INSERT LESSON
    private val _newLesson = MutableLiveData(Lesson())
    val newLesson : LiveData<Lesson> = _newLesson
    fun setNewLesson(nl: Lesson){
        _newLesson.value = nl
        if (nl.id != null){
            viewModelScope.launch{
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
        viewModelScope.launch {
            insertLesson(newLesson.value!!)
        }
    }

    //INSERT TASK
    private val _newTask = MutableLiveData(Task())
    val newTask : LiveData<Task> = _newTask
    fun setNewTask(nt: Task){
        _newTask.value = nt
        if (nt.id != null){
            viewModelScope.launch{
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
        viewModelScope.launch {
            insertTask(newTask.value!!)
        }
    }

    //INSERT USER
    private val _newUser = MutableLiveData(User())
    val newUser : LiveData<User> = _newUser
    fun setNewUser(nu: User){
        _newUser.value = nu
    }

    //INSERT LANGUAGE
    private val _newLanguage = MutableLiveData(Language())
    val newLanguage: LiveData<Language> = _newLanguage
    fun setNewLanguage(nl: Language){
        _newLanguage.value = nl
    }

    fun onInsertLanguageButtonClick(){
        viewModelScope.launch {
            insertLanguage(newLanguage.value!!)
        }
    }

    suspend fun createUserWithEmailAndPassword(email: String, password: String): com.google.android.gms.tasks.Task<AuthResult>{
        return authFirestore.signInWithEmailAndPassword(email, password)
    }
    suspend fun signInWithEmailAndPassword(email: String, password: String): com.google.android.gms.tasks.Task<AuthResult>{
        return authFirestore.signInWithEmailAndPassword(email, password)
    }
    suspend fun signOut(): Unit{
        return authFirestore.signOut()
    }
}