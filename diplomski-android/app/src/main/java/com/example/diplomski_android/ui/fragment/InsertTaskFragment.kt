package com.example.diplomski_android.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.diplomski_android.R
import com.example.diplomski_android.databinding.FragmentInsertTaskBinding
import com.example.diplomski_android.model.Chapter
import com.example.diplomski_android.model.Course
import com.example.diplomski_android.model.Lesson
import com.example.diplomski_android.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class InsertTaskFragment : Fragment() {

    private val mainViewModel : MainViewModel by activityViewModels()
    private lateinit var binding: FragmentInsertTaskBinding
    var courses = listOf<Course>()
    var chapters = listOf<Chapter>()
    var lessons = listOf<Lesson>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInsertTaskBinding.inflate(inflater, container, false)

        mainViewModel.getCourses()
        lifecycleScope.launchWhenCreated {
            mainViewModel.coursesStateFlow.collectLatest { coursesFlow ->
                courses = coursesFlow
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            chapters = mainViewModel.getChaptersByCourse(mainViewModel.newTask.value?.course?.id ?: 0)
            lessons = mainViewModel.getLessonsByChapters(mainViewModel.newTask.value?.chapter?.id ?: 0)
        }

        textChangeListener()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val courseAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item, courses)
        var chapterAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item, chapters)
        var lessonAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item, lessons)

        binding.apply {
            actvTaskCourse.setAdapter(courseAdapter)
            actvTaskChapter.setAdapter(chapterAdapter)
            actvTaskLesson.setAdapter(lessonAdapter)
        }

        mainViewModel.newTask.value?.id?.let {
            binding.apply {
                actvTaskCourse.setText(mainViewModel.newTask.value?.course?.name)
                actvTaskChapter.setText(mainViewModel.newTask.value?.chapter?.name)
                actvTaskLesson.setText(mainViewModel.newTask.value?.lesson?.lesson_type)
            }
        } ?: run {
            binding.apply {
                actvTaskCourse.setText("")
                actvTaskChapter.setText("")
                actvTaskLesson.setText("")
            }
            mainViewModel.newTask.value?.lesson_id = mainViewModel.currentLesson.value?.id
        }

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = mainViewModel
            insertTaskFragment = this@InsertTaskFragment
        }

        binding.actvTaskCourse.setOnItemClickListener { _, _, position, _ ->
            val course = courseAdapter.getItem(position)

            binding.actvTaskChapter.setText("")
            binding.actvTaskLesson.setText("")
            mainViewModel.newTask.value?.lesson_id = null
            binding.menuInsertTaskCourses.helperText = validateCourse()

            CoroutineScope(Dispatchers.IO).launch {
                course?.let{
                    val courseId = course.id
                    courseId?.let{
                        chapters = mainViewModel.getChaptersByCourse(courseId)
                    }
                }
                lifecycleScope.launch {
                    chapterAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item, chapters)
                    binding.actvTaskChapter.setAdapter(chapterAdapter)
                    binding.actvTaskLesson.setAdapter(ArrayAdapter(requireContext(), R.layout.spinner_item, listOf<Lesson>()))
                }
            }
        }

        binding.actvTaskChapter.setOnItemClickListener { _, _, position, _ ->
            val chapter = chapterAdapter.getItem(position)

            binding.actvTaskLesson.setText("")
            mainViewModel.newTask.value?.lesson_id = null
            binding.menuInsertTaskChapters.helperText = validateChapter()

            CoroutineScope(Dispatchers.IO).launch {
                chapter?.let{
                    val chapterId = chapter.id
                    chapterId?.let{
                        lessons = mainViewModel.getLessonsByChapters(chapterId)
                    }
                }
                lifecycleScope.launch {
                    lessonAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item, lessons)
                    binding.actvTaskLesson.setAdapter(lessonAdapter)
                }
            }
        }

        binding.actvTaskLesson.setOnItemClickListener { _, _, position, _ ->
            mainViewModel.onTaskLessonItemSelected(lessonAdapter, position)
            binding.menuInsertTaskLessons.helperText = validateLesson()
        }

        binding.buttonInsertTaskConfirm.setOnClickListener {
            if(validateOnComplete()){
                mainViewModel.onInsertTaskButtonClick()
                activity?.onBackPressed()
            }
        }
    }

    private fun textChangeListener() {
        binding.insertTaskQuestion.doOnTextChanged { _, _, _, _ ->
            binding.insertTaskQuestionContainer.helperText = validateQuestion()
        }
        binding.insertTaskAnswer.doOnTextChanged { _, _, _, _ ->
            binding.insertTaskAnswerContainer.helperText = validateAnswer()
        }
    }

    private fun validateCourse(): String? {
        val course = binding.actvTaskCourse.text.toString()
        if (course == "") {
            return "Required"
        }
        return null
    }

    private fun validateChapter(): String? {
        val chapter = binding.actvTaskChapter.text.toString()
        if (chapter == "") {
            return "Required"
        }
        return null
    }

    private fun validateLesson(): String? {
        val lesson = binding.actvTaskLesson.text.toString()
        if (lesson == "") {
            return "Required"
        }
        return null
    }

    private fun validateQuestion(): String? {
        val question = binding.insertTaskQuestion.text.toString()
        if (question == "") {
            return "Required"
        }
        return null
    }

    private fun validateAnswer(): String? {
        val answer = binding.insertTaskAnswer.text.toString()
        if (answer == "") {
            return "Required"
        }
        return null
    }

    private fun validateOnComplete(): Boolean {
        binding.apply {
            menuInsertTaskCourses.helperText = validateCourse()
            menuInsertTaskChapters.helperText = validateChapter()
            menuInsertTaskLessons.helperText = validateLesson()
            insertTaskQuestionContainer.helperText = validateQuestion()
            insertTaskAnswerContainer.helperText = validateAnswer()
        }

        if (binding.menuInsertTaskCourses.helperText == null &&
            binding.menuInsertTaskChapters.helperText == null &&
            binding.menuInsertTaskLessons.helperText == null &&
            binding.insertTaskQuestionContainer.helperText == null &&
            binding.insertTaskAnswerContainer.helperText == null){
            return true
        }

        return false
    }
}