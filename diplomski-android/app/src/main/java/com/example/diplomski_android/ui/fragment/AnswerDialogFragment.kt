package com.example.diplomski_android.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.diplomski_android.MainViewModel
import com.example.diplomski_android.databinding.DialogAnswerBinding


class AnswerDialogFragment: DialogFragment() {
    private val mainViewModel : MainViewModel by activityViewModels()
    private lateinit var binding: DialogAnswerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogAnswerBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = mainViewModel
            answerDialogFragment = this@AnswerDialogFragment
        }

        binding.buttonNextTask.setOnClickListener {
            mainViewModel.onDialogNextButtonClick()
            dismiss()
        }
    }
}