package com.example.diplomski_android.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.diplomski_android.R
import com.example.diplomski_android.databinding.FragmentInsertLessonBinding
import com.example.diplomski_android.model.Chapter
import com.example.diplomski_android.model.Course
import com.example.diplomski_android.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class InsertLessonFragment : Fragment() {

    private val mainViewModel : MainViewModel by activityViewModels()
    private lateinit var binding: FragmentInsertLessonBinding
    var courses = listOf<Course>()
    var chapters = listOf<Chapter>()
    private val lessonTypes = listOf("Translating","Vocabulary","Speaking","Listening")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInsertLessonBinding.inflate(inflater, container, false)

        mainViewModel.getCourses()
        lifecycleScope.launchWhenCreated {
            mainViewModel.coursesStateFlow.collectLatest { coursesFlow ->
                courses = coursesFlow
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            chapters = mainViewModel.getChaptersByCourse(mainViewModel.newLesson.value?.course?.id ?: 0)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val courseAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item, courses)
        var chapterAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item, chapters)
        val typeAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item, lessonTypes)

        binding.apply {
            actvLessonCourse.setAdapter(courseAdapter)
            actvLessonChapter.setAdapter(chapterAdapter)
            actvLessonType.setAdapter(typeAdapter)
        }

        mainViewModel.newLesson.value?.id?.let {
            binding.apply {
                actvLessonCourse.setText(mainViewModel.newLesson.value?.course?.name)
                actvLessonChapter.setText(mainViewModel.newLesson.value?.chapter?.name)
                actvLessonType.setText(mainViewModel.newLesson.value?.lesson_type)
            }
        } ?: run {
            binding.apply {
                actvLessonCourse.setText("")
                actvLessonChapter.setText("")
                actvLessonType.setText("")
            }
            mainViewModel.newLesson.value?.chapter_id = mainViewModel.newLesson.value?.chapter?.id
        }

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = mainViewModel
            insertLessonFragment = this@InsertLessonFragment
        }

//      TODO: ???
        binding.actvLessonCourse.setOnItemClickListener { _, _, position, _ ->
            val course = courseAdapter.getItem(position)
            binding.actvLessonChapter.setText("")
            mainViewModel.newLesson.value?.chapter_id = null
            binding.menuInsertLessonCourses.helperText = validateCourse()

            CoroutineScope(Dispatchers.IO).launch {
                course?.let{
                    val courseId = course.id
                    courseId?.let{
                        chapters = mainViewModel.getChaptersByCourse(courseId)
                    }
                }
                lifecycleScope.launch{
                    chapterAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item, chapters)
                    binding.actvLessonChapter.setAdapter(chapterAdapter)
                }
            }
        }

        binding.actvLessonChapter.setOnItemClickListener { _, _, position, _ ->
            mainViewModel.onLessonChapterItemSelected(chapterAdapter, position)
            binding.menuInsertLessonChapters.helperText = validateChapter()
        }

        binding.actvLessonType.setOnItemClickListener{ _, _, position, _ ->
            mainViewModel.onLessonTypeItemSelected(typeAdapter, position)
            binding.menuInsertLessonType.helperText = validateType()
        }

        binding.buttonInsertLessonConfirm.setOnClickListener {
            if (validateOnConfirm()){
                mainViewModel.onInsertLessonButtonClick()
                activity?.onBackPressed()
            }
        }
    }

    private fun validateCourse(): String?{
        val course = binding.actvLessonCourse.text.toString()
        if (course == ""){
            return "Required"
        }
        return null
    }

    private fun validateChapter(): String?{
        val chapter = binding.actvLessonChapter.text.toString()
        if (chapter == ""){
            return "Required"
        }
        return null
    }

    private fun validateType(): String?{
        val type = binding.actvLessonType.text.toString()
        if (type == ""){
            return "Required"
        }
        return null
    }

    private fun validateOnConfirm(): Boolean{
        binding.apply {
            menuInsertLessonCourses.helperText = validateCourse()
            menuInsertLessonChapters.helperText = validateChapter()
            menuInsertLessonType.helperText = validateType()
        }

        if (binding.menuInsertLessonCourses.helperText == null &&
            binding.menuInsertLessonChapters.helperText == null &&
            binding.menuInsertLessonType.helperText == null){
            return true
        }

        return false
    }
}