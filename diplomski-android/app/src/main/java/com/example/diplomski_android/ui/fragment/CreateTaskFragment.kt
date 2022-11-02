package com.example.diplomski_android.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.diplomski_android.MainViewModel
import com.example.diplomski_android.R
import com.example.diplomski_android.databinding.FragmentCreateTaskBinding
import com.example.diplomski_android.model.Chapter
import com.example.diplomski_android.model.Course
import com.example.diplomski_android.model.Lesson
import com.example.diplomski_android.model.Task
import kotlinx.android.synthetic.main.fragment_create_lesson.*
import kotlinx.android.synthetic.main.fragment_create_task.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CreateTaskFragment : Fragment() {

    private val mainViewModel : MainViewModel by activityViewModels()
    private var createTaskBinding: FragmentCreateTaskBinding? = null
    var courses = listOf<Course>()
    var chapters = listOf<Chapter>()
    var lessons = listOf<Lesson>()
    var createTask = Task()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentCreateTaskBinding.inflate(inflater, container, false)
        createTaskBinding = fragmentBinding

        CoroutineScope(Dispatchers.IO).launch {
            courses = mainViewModel.getCourses()
        }

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createTaskBinding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = mainViewModel
            createTaskFragment = this@CreateTaskFragment
        }

        //TODO: ???
        val courseAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item, courses)
        lateinit var chapterAdapter: ArrayAdapter<Chapter>
        lateinit var lessonAdapter: ArrayAdapter<Lesson>

        actv_task_course.setAdapter(courseAdapter)
        actv_task_course.setOnItemClickListener { _, _, position, _ ->
            val course = courseAdapter.getItem(position)
            actv_task_chapter.setText("")
            actv_task_lesson.setText("")
            val jobChapters = CoroutineScope(Dispatchers.IO).launch {
                chapters = mainViewModel.getChaptersByCourse(course?.id!!)
                chapterAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item, chapters)
            }
            runBlocking {
                jobChapters.join()
            }
            actv_task_chapter.setAdapter(chapterAdapter)
        }

        actv_task_chapter.setOnItemClickListener { _, _, position, _ ->
            val chapter = chapterAdapter.getItem(position)
            actv_task_lesson.setText("")
            val jobLessons = CoroutineScope(Dispatchers.IO).launch {
                lessons = mainViewModel.getLessonsByChapter(chapter?.id!!)
                lessonAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item, lessons)
            }
            runBlocking {
                jobLessons.join()
            }
            actv_task_lesson.setAdapter(lessonAdapter)
        }

        actv_task_lesson.setOnItemClickListener { _, _, position, _ ->
            val lesson = lessonAdapter.getItem(position)
            createTask.lesson_id = lesson?.id
        }

        button_create_task_confirm.setOnClickListener {
            createTask.question = ti_create_task_question.editText?.text.toString()
            createTask.answer = ti_create_task_answer.editText?.text.toString()
            CoroutineScope(Dispatchers.IO).launch {
                mainViewModel.insertTask(createTask)
            }
        }
    }
}