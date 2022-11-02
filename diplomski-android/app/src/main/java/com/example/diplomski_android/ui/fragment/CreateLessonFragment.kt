package com.example.diplomski_android.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.diplomski_android.MainViewModel
import com.example.diplomski_android.R
import com.example.diplomski_android.databinding.FragmentCreateLessonBinding
import com.example.diplomski_android.model.Chapter
import com.example.diplomski_android.model.Course
import com.example.diplomski_android.model.Lesson
import kotlinx.android.synthetic.main.fragment_create_lesson.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CreateLessonFragment : Fragment() {

    private val mainViewModel : MainViewModel by activityViewModels()
    private var createLessonBinding: FragmentCreateLessonBinding? = null
    var courses = listOf<Course>()
    var chapters = listOf<Chapter>()
    var createLesson = Lesson()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentCreateLessonBinding.inflate(inflater, container, false)
        createLessonBinding = fragmentBinding

        CoroutineScope(Dispatchers.IO).launch {
            courses = mainViewModel.getCourses()
        }

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createLessonBinding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = mainViewModel
            createLessonFragment = this@CreateLessonFragment
        }

        //TODO: ???
        val courseAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item, courses)
        lateinit var chapterAdapter: ArrayAdapter<Chapter>

        actv_lesson_course.setAdapter(courseAdapter)
        actv_lesson_course.setOnItemClickListener { _, _, position, _ ->
            val course = courseAdapter.getItem(position)
            actv_lesson_chapter.setText("")
            val jobChapters = CoroutineScope(Dispatchers.IO).launch {
                chapters = mainViewModel.getChaptersByCourse(course?.id!!)
                chapterAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item, chapters)
            }
            runBlocking {
                jobChapters.join()
            }
            actv_lesson_chapter.setAdapter(chapterAdapter)
        }

        actv_lesson_chapter.setOnItemClickListener { _, _, position, _ ->
            val chapter = chapterAdapter.getItem(position)
            createLesson.chapter_id = chapter?.id
        }

        //TODO: Privremeno
        val typeAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item,
            listOf("Translating","Vocabulary","Speaking","Listening"))
        actv_lesson_type.setAdapter(typeAdapter)
        actv_lesson_type.setOnItemClickListener { _, _, position, _ ->
            createLesson.lesson_type = typeAdapter.getItem(position)
        }

        button_create_lesson_confirm.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                mainViewModel.insertLesson(createLesson)
            }
        }
    }
}