package com.example.diplomski_android.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.diplomski_android.MainViewModel
import com.example.diplomski_android.databinding.DialogResultBinding
import kotlinx.android.synthetic.main.dialog_result.*

class ResultDialogFragment: DialogFragment() {
    private val mainViewModel : MainViewModel by activityViewModels()
    private var resultBinding: DialogResultBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = DialogResultBinding.inflate(inflater, container, false)
        resultBinding = fragmentBinding

        return resultBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        resultBinding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = mainViewModel
            resultDialogFragment = this@ResultDialogFragment
        }

        button_result_continue.setOnClickListener {
            mainViewModel.onResultNextButtonClick()
            dismiss()
            activity?.onBackPressed()
        }
    }
}