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
import com.example.diplomski_android.databinding.FragmentInsertTaskBinding
import com.example.diplomski_android.model.Chapter
import com.example.diplomski_android.model.Course
import com.example.diplomski_android.model.Lesson
import kotlinx.android.synthetic.main.fragment_insert_task.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class InsertTaskFragment : Fragment() {

    private val mainViewModel : MainViewModel by activityViewModels()
    private var insertTaskBinding: FragmentInsertTaskBinding? = null
    var courses = listOf<Course>()
    var chapters = listOf<Chapter>()
    var lessons = listOf<Lesson>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentInsertTaskBinding.inflate(inflater, container, false)
        insertTaskBinding = fragmentBinding

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
        actv_task_course.setAdapter(courseAdapter)

        lateinit var chapterAdapter: ArrayAdapter<Chapter>

        lateinit var lessonAdapter: ArrayAdapter<Lesson>

        insertTaskBinding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = mainViewModel
            insertTaskFragment = this@InsertTaskFragment
        }

//      TODO: ???
        actv_task_course.setOnItemClickListener { _, _, position, _ ->
            val course = courseAdapter.getItem(position)

            actv_task_chapter.setText("")
            actv_task_lesson.setText("")
            mainViewModel.newTask.value?.lesson_id = null

            mainViewModel.getChaptersByCourse(course?.id!!)
            lifecycleScope.launchWhenCreated {
                mainViewModel.chaptersStateFlow.collectLatest {
                    chapters = it
                    chapterAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item, chapters)
                }
            }
            actv_task_chapter.setAdapter(chapterAdapter)
        }

        actv_task_chapter.setOnItemClickListener { _, _, position, _ ->
            val chapter = chapterAdapter.getItem(position)

            actv_task_lesson.setText("")
            mainViewModel.newTask.value?.lesson_id = null

            mainViewModel.getLessonsByChapter(chapter?.id!!)
            lifecycleScope.launchWhenCreated {
                mainViewModel.lessonsStateFlow.collectLatest {
                    lessons = it
                    lessonAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item, lessons)
                }
            }
            actv_task_lesson.setAdapter(lessonAdapter)
        }

        actv_task_lesson.setOnItemClickListener { _, _, position, _ ->
            mainViewModel.onTaskLessonItemSelected(lessonAdapter, position)
        }
    }
}