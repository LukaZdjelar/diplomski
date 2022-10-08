package com.example.diplomski_android.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.diplomski_android.MainActivity
import com.example.diplomski_android.databinding.FragmentTaskBinding
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
    ): View {
        val fragmentBinding = FragmentTaskBinding.inflate(inflater, container, false)
        taskBinding = fragmentBinding

        setTasks()
        setTaskToShow()

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

    private fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun setTasks(){
        tasks = mainViewModel.lesson.value?.tasks!!
    }

    private fun setTaskToShow(){
        if (taskNumber < tasks.size){
            mainViewModel.setTask(tasks[taskNumber])
        }else{
            Toast.makeText(context, "No more tasks", Toast.LENGTH_SHORT).show()
            activity?.onBackPressed()
        }
    }

    private fun onButtonClick(){
        buttonTest.setOnClickListener {
            if (editTextAnswer.text.toString() == mainViewModel.task.value?.answer){
                Toast.makeText(context,"Tacno",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(context,"Netacno",Toast.LENGTH_SHORT).show()
            }
            editTextAnswer.text = null
            taskNumber++
            it.hideKeyboard()
            setTaskToShow()
        }
    }
}