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
import com.example.diplomski_android.databinding.FragmentTasksBinding
import com.example.diplomski_android.model.Task
import com.example.diplomski_android.ui.adapter.TasksAdapter
import kotlinx.coroutines.flow.collectLatest

class TasksFragment : Fragment() {

    private val mainViewModel : MainViewModel by activityViewModels()
    private lateinit var binding : FragmentTasksBinding
    private lateinit var tasksAdapter: TasksAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTasksBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = mainViewModel
            tasksFragment = this@TasksFragment
        }

        binding.buttonCreateTask.setOnClickListener {
            val task = Task()
            task.course = mainViewModel.currentCourse.value
            task.chapter = mainViewModel.currentChapter.value
            task.lesson = mainViewModel.currentLesson.value
            mainViewModel.setNewTask(task)
            Navigation.findNavController(view).navigate(R.id.action_tasksFragment_to_insertTaskFragment)
        }

        setupRecyclerView()
        lifecycleScope.launchWhenCreated {
            mainViewModel.tasksStateFlow.collectLatest {
                tasksAdapter.differ.submitList(it)
            }
        }
    }

    private fun setupRecyclerView(){
        tasksAdapter = TasksAdapter(mainViewModel)
        binding.rvTasks.apply {
            adapter = tasksAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}