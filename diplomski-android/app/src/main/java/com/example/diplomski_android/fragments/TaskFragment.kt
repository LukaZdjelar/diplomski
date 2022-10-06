package com.example.diplomski_android.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.diplomski_android.databinding.FragmentTaskBinding
import com.example.diplomski_android.model.Lesson
import com.example.diplomski_android.model.Task
import com.example.diplomski_android.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_task.*

class TaskFragment : Fragment() {
    private val mainViewModel : MainViewModel by activityViewModels()
    private var taskBinding : FragmentTaskBinding? = null
    private lateinit var  tasks: List<Task>
    private var taskNumber: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentTaskBinding.inflate(inflater, container, false)
        taskBinding = fragmentBinding

        tasks = mainViewModel.lesson.value?.tasks!!
        setTask()

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        taskBinding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = mainViewModel
            taskFragment = this@TaskFragment
        }
        onButtonClick()
    }

    private fun setTask(){
        if (tasks.isNotEmpty() && (taskNumber < tasks.size)){
            mainViewModel.setTask(tasks[taskNumber])
        }else{
            Toast.makeText(context, "No more tasks", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onButtonClick(){
        buttonTest.setOnClickListener {
            taskNumber++
            setTask()
        }
    }
}