package com.example.diplomski_android.ui.fragment

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.diplomski_android.databinding.FragmentTaskBinding
import com.example.diplomski_android.model.Task
import com.example.diplomski_android.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskFragment : Fragment() {
    private val mainViewModel : MainViewModel by activityViewModels()
    private lateinit var binding : FragmentTaskBinding
    private lateinit var task : Task

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTaskBinding.inflate(inflater, container, false)

        mainViewModel.taskNumber.value?.let { number ->
            mainViewModel.tasks.value?.get(number)?.let { task0 ->
                task = task0
            }
        }
        mainViewModel.setTask(task)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = mainViewModel
            taskFragment = this@TaskFragment
        }

//      TODO: ???
        binding.buttonAnswer.setOnClickListener {
            mainViewModel.onAnswerButtonClick()
            hideKeyboard()
            AlertDialog.Builder(context)
                .setTitle("${mainViewModel.correct.value}")
                .setMessage("Answer was ${mainViewModel.task.value?.answer}")
                .setPositiveButton("Next task"){ _, _ ->
                    mainViewModel.onDialogNextButtonClick()
                }
                .setCancelable(false)
                .show()
            if (mainViewModel.taskNumber.value?.plus(1) == mainViewModel.tasksStateFlow.value.size){
                calculateGrade()
            }
        }
        showResultsOnComplete()
    }

    private fun calculateGrade(){
        CoroutineScope(Dispatchers.IO).launch {
            mainViewModel.onLessonComplete()
        }
    }

    private fun showResultsOnComplete(){
        mainViewModel.completed.observe(viewLifecycleOwner) { completed ->
            if (completed) {
                AlertDialog.Builder(context)
                    .setTitle("${mainViewModel.passed.value}")
                    .setMessage("Your grade is ${mainViewModel.grade.value}")
                    .setPositiveButton("OK") { _, _ ->
                        mainViewModel.onResultNextButtonClick()
                        activity?.onBackPressed()
                    }
                    .setCancelable(false)
                    .show()
            }
        }
    }

    private fun Fragment.hideKeyboard() {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    override fun onDetach() {
        super.onDetach()

        mainViewModel.setTask(Task())
        mainViewModel.setTaskNumber(0)
        mainViewModel.setCompleted(false)
        mainViewModel.setCorrectCounter(0)
    }
}