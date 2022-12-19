package com.example.diplomski_android.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diplomski_android.MainViewModel
import com.example.diplomski_android.R
import com.example.diplomski_android.databinding.FragmentLessonsBinding
import com.example.diplomski_android.model.Lesson
import com.example.diplomski_android.ui.adapter.LessonsAdapter
import kotlinx.coroutines.flow.collectLatest

class LessonsFragment : Fragment() {
    private val mainViewModel : MainViewModel by activityViewModels()
    private lateinit var binding : FragmentLessonsBinding
    private lateinit var lessonsAdapter: LessonsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLessonsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = mainViewModel
            lessonsFragment = this@LessonsFragment
        }

        binding.buttonCreateLesson.setOnClickListener {
            val lesson = Lesson()
            lesson.course = mainViewModel.currentCourse.value
            lesson.chapter = mainViewModel.currentChapter.value
            mainViewModel.setNewLesson(lesson)
            Navigation.findNavController(view).navigate(R.id.action_lessonsFragment_to_insertLessonFragment)
        }

        setupRecyclerView()
    }

    private fun setupRecyclerView(){
        lessonsAdapter = LessonsAdapter(mainViewModel)
        binding.rvLessons.apply {
            adapter = lessonsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.currentChapter.value?.let { chapter ->
            chapter.id?.let { id ->
                mainViewModel.getLessonsByChapterFlow(id)
            }
        }

        lifecycleScope.launchWhenResumed {
            mainViewModel.lessonsStateFlow.collectLatest {
                lessonsAdapter.differ.submitList(it)
            }
        }
    }
}