package com.example.diplomski_android.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.diplomski_android.MainViewModel
import com.example.diplomski_android.databinding.DialogAnswerBinding
import kotlinx.android.synthetic.main.dialog_answer.*


class AnswerDialogFragment: DialogFragment() {
    private val mainViewModel : MainViewModel by activityViewModels()
    private var answerBinding: DialogAnswerBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = DialogAnswerBinding.inflate(inflater, container, false)
        answerBinding = fragmentBinding

        return answerBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        answerBinding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = mainViewModel
            answerDialogFragment = this@AnswerDialogFragment
        }

        button_next_task.setOnClickListener {
            mainViewModel.onDialogNextButtonClick()
            dismiss()
        }
    }
}