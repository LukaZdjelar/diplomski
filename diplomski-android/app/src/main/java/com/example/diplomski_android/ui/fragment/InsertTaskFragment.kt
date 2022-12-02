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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

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
            mainViewModel.coursesStateFlow.collectLatest {
                courses = it
            }
        }

        if (mainViewModel.newTask.value?.id != null){
            CoroutineScope(Dispatchers.IO).launch {
                chapters = mainViewModel.getChaptersByCourse(mainViewModel.newTask.value?.course!!.id!!)
                lessons = mainViewModel.getLessonsByChapters(mainViewModel.newTask.value?.chapter!!.id!!)
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val courseAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item, courses)
        binding.actvTaskCourse.setAdapter(courseAdapter)

        lateinit var chapterAdapter: ArrayAdapter<Chapter>

        lateinit var lessonAdapter: ArrayAdapter<Lesson>

        if (mainViewModel.newTask.value?.id == null){
            binding.actvTaskCourse.setText(mainViewModel.newTask.value?.course!!.name,false)
            binding.actvTaskChapter.setText(mainViewModel.newTask.value?.chapter!!.name,false)
            binding.actvTaskLesson.setText(mainViewModel.newTask.value?.lesson!!.lesson_type,false)
            mainViewModel.newTask.value?.lesson_id = mainViewModel.currentLesson.value?.id
        }else{
            binding.actvTaskCourse.setText(mainViewModel.newTask.value?.course!!.name,false)
            binding.actvTaskChapter.setText(mainViewModel.newTask.value?.chapter!!.name,false)
            binding.actvTaskLesson.setText(mainViewModel.newTask.value?.lesson!!.lesson_type,false)

            chapterAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item, chapters)
            binding.actvTaskChapter.setAdapter(chapterAdapter)
            lessonAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item, lessons)
            binding.actvTaskLesson.setAdapter(lessonAdapter)
        }

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = mainViewModel
            insertTaskFragment = this@InsertTaskFragment
        }

//      TODO: ???
        binding.actvTaskCourse.setOnItemClickListener { _, _, position, _ ->
            val course = courseAdapter.getItem(position)

            binding.actvTaskChapter.setText("")
            binding.actvTaskLesson.setText("")
            mainViewModel.newTask.value?.lesson_id = null

            val job = CoroutineScope(Dispatchers.IO).launch {
                chapters = mainViewModel.getChaptersByCourse(course?.id!!)
            }

            runBlocking { job.join() }

            chapterAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item, chapters)
            binding.actvTaskChapter.setAdapter(chapterAdapter)
        }

        binding.actvTaskChapter.setOnItemClickListener { _, _, position, _ ->
            val chapter = chapterAdapter.getItem(position)

            binding.actvTaskLesson.setText("")
            mainViewModel.newTask.value?.lesson_id = null

            val job = CoroutineScope(Dispatchers.IO).launch {
                lessons = mainViewModel.getLessonsByChapters(chapter?.id!!)
            }

            runBlocking { job.join() }

            lessonAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item, lessons)
            binding.actvTaskLesson.setAdapter(lessonAdapter)
        }

        binding.actvTaskLesson.setOnItemClickListener { _, _, position, _ ->
            mainViewModel.onTaskLessonItemSelected(lessonAdapter, position)
        }

        binding.buttonInsertTaskConfirm.setOnClickListener {
            mainViewModel.onInsertTaskButtonClick()
            activity?.onBackPressed()
        }
    }
}