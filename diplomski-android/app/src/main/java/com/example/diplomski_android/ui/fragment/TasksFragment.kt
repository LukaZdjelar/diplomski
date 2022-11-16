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
import com.example.diplomski_android.model.Chapter
import com.example.diplomski_android.model.Task
import com.example.diplomski_android.ui.adapter.TaskAdapter
import kotlinx.android.synthetic.main.fragment_tasks.*
import kotlinx.coroutines.flow.collectLatest

class TasksFragment : Fragment() {

    private val mainViewModel : MainViewModel by activityViewModels()
    private var tasksBinding : FragmentTasksBinding? = null
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentTasksBinding.inflate(inflater, container, false)
        tasksBinding = fragmentBinding

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tasksBinding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = mainViewModel
            tasksFragment = this@TasksFragment
        }

        button_create_task.setOnClickListener {
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
                taskAdapter.differ.submitList(it)
            }
        }
    }

    private fun setupRecyclerView(){
        taskAdapter = TaskAdapter(mainViewModel)
        rvTasks.apply {
            adapter = taskAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}