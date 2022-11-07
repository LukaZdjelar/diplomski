package com.example.diplomski_android.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.diplomski_android.MainViewModel
import com.example.diplomski_android.R
import com.example.diplomski_android.databinding.FragmentInsertLessonBinding
import com.example.diplomski_android.model.Chapter
import com.example.diplomski_android.model.Course
import com.example.diplomski_android.model.Lesson
import kotlinx.android.synthetic.main.fragment_insert_chapter.*
import kotlinx.android.synthetic.main.fragment_insert_lesson.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class InsertLessonFragment : Fragment() {

    private val mainViewModel : MainViewModel by activityViewModels()
    private var insertLessonBinding: FragmentInsertLessonBinding? = null
    var courses = listOf<Course>()
    var chapters = listOf<Chapter>()
    var insertLesson = Lesson()
    val lessonTypes = listOf("Translating","Vocabulary","Speaking","Listening")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentInsertLessonBinding.inflate(inflater, container, false)
        insertLessonBinding = fragmentBinding

        mainViewModel.getCourses()
        lifecycleScope.launchWhenCreated {
            mainViewModel.coursesStateFlow.collectLatest {
                courses = it
            }
        }

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val courseAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item, courses)
        actv_lesson_course.setAdapter(courseAdapter)

        lateinit var chapterAdapter: ArrayAdapter<Chapter>

        val typeAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item, lessonTypes)
        actv_lesson_type.setAdapter(typeAdapter)

        if (mainViewModel.newLesson.value?.id == null){
            actv_lesson_course.setText("",false)
            actv_lesson_chapter.setText("",false)
            actv_lesson_type.setText("",false)
        }else{
            actv_lesson_course.setText(mainViewModel.newLesson.value?.course!!.name,false)
            actv_lesson_chapter.setText(mainViewModel.newLesson.value?.chapter!!.name,false)
            actv_lesson_type.setText(mainViewModel.newLesson.value?.lesson_type!!,false)
        }

        insertLessonBinding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = mainViewModel
            insertLessonFragment = this@InsertLessonFragment
        }

//      TODO: ???
        actv_lesson_course.setOnItemClickListener { _, _, position, _ ->
            val course = courseAdapter.getItem(position)
            actv_lesson_chapter.setText("")
            mainViewModel.newLesson.value?.chapter_id = null

            mainViewModel.getChaptersByCourse(course?.id!!)
            lifecycleScope.launchWhenCreated {
                mainViewModel.chaptersStateFlow.collectLatest {
                    chapters = it
                    chapterAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item, chapters)
                }
            }

            actv_lesson_chapter.setAdapter(chapterAdapter)
        }

        actv_lesson_chapter.setOnItemClickListener { _, _, position, _ ->
            mainViewModel.onLessonChapterItemSelected(chapterAdapter, position)
        }

        actv_lesson_type.setOnItemClickListener{_, _, position, _ ->
            mainViewModel.onLessonTypeItemSelected(typeAdapter, position)
        }
    }
}