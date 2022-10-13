package com.example.diplomski_android.ui.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.diplomski_android.databinding.FragmentTaskBinding
import com.example.diplomski_android.ui.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_task.*

class TaskFragment : Fragment() {
    private val mainViewModel : MainViewModel by activityViewModels()
    private var taskBinding : FragmentTaskBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentTaskBinding.inflate(inflater, container, false)
        taskBinding = fragmentBinding

//      TODO: ???
        mainViewModel.setTask(mainViewModel.lesson.value?.tasks!![mainViewModel.taskNumber.value!!])

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        taskBinding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = mainViewModel
            taskFragment = this@TaskFragment
        }

//      TODO: ???
        buttonAnswer.setOnClickListener {
            hideKeyboard()
            mainViewModel.onAnswerButtonClick()
        }
        goBackOnComplete()
    }

    private fun goBackOnComplete(){
        mainViewModel.completed.observe(viewLifecycleOwner) {
            if (mainViewModel.completed.value == true){
                activity?.onBackPressed()
            }
        }
    }

    private fun Fragment.hideKeyboard() {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    override fun onDetach() {
        super.onDetach()

        mainViewModel.setTask(null)
        mainViewModel.setTaskNumber(0)
        mainViewModel.setCompleted(false)
    }
}