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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class InsertLessonFragment : Fragment() {

    private val mainViewModel : MainViewModel by activityViewModels()
    private lateinit var binding: FragmentInsertLessonBinding
    var courses = listOf<Course>()
    var chapters = listOf<Chapter>()
    val lessonTypes = listOf("Translating","Vocabulary","Speaking","Listening")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInsertLessonBinding.inflate(inflater, container, false)

        mainViewModel.getCourses()
        lifecycleScope.launchWhenCreated {
            mainViewModel.coursesStateFlow.collectLatest {
                courses = it
            }
        }

        if (mainViewModel.newLesson.value?.id != null){
            CoroutineScope(Dispatchers.IO).launch {
                chapters = mainViewModel.getChaptersByCourse(mainViewModel.newLesson.value?.course!!.id!!)
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val courseAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item, courses)
        binding.actvLessonCourse.setAdapter(courseAdapter)

        lateinit var chapterAdapter: ArrayAdapter<Chapter>

        val typeAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item, lessonTypes)
        binding.actvLessonType.setAdapter(typeAdapter)

        if (mainViewModel.newLesson.value?.id == null){
            binding.actvLessonCourse.setText(mainViewModel.newLesson.value?.course!!.name,false)
            binding.actvLessonChapter.setText(mainViewModel.newLesson.value?.chapter!!.name,false)
            mainViewModel.newLesson.value?.chapter_id = mainViewModel.newLesson.value?.chapter?.id
            binding.actvLessonType.setText("",false)
        }else{
            binding.actvLessonCourse.setText(mainViewModel.newLesson.value?.course!!.name,false)
            binding.actvLessonChapter.setText(mainViewModel.newLesson.value?.chapter!!.name,false)
            binding.actvLessonType.setText(mainViewModel.newLesson.value?.lesson_type!!,false)

            chapterAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item, chapters)
            binding.actvLessonChapter.setAdapter(chapterAdapter)
        }

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = mainViewModel
            insertLessonFragment = this@InsertLessonFragment
        }

//      TODO: ???
        binding.actvLessonCourse.setOnItemClickListener { _, _, position, _ ->
            val course = courseAdapter.getItem(position)
            binding.actvLessonChapter.setText("")
            mainViewModel.newLesson.value?.chapter_id = null

            val job = CoroutineScope(Dispatchers.IO).launch {
                chapters = mainViewModel.getChaptersByCourse(course?.id!!)
            }

            runBlocking { job.join() }

            chapterAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item, chapters)
            binding.actvLessonChapter.setAdapter(chapterAdapter)
        }

        binding.actvLessonChapter.setOnItemClickListener { _, _, position, _ ->
            mainViewModel.onLessonChapterItemSelected(chapterAdapter, position)
        }

        binding.actvLessonType.setOnItemClickListener{ _, _, position, _ ->
            mainViewModel.onLessonTypeItemSelected(typeAdapter, position)
        }

        binding.buttonInsertLessonConfirm.setOnClickListener {
            mainViewModel.onInsertLessonButtonClick()
            activity?.onBackPressed()
        }
    }
}