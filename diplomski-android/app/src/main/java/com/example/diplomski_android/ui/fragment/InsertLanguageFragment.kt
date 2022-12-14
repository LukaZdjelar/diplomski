package com.example.diplomski_android.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.diplomski_android.databinding.FragmentInsertLanguageBinding
import com.example.diplomski_android.viewmodel.MainViewModel

class InsertLanguageFragment : Fragment() {

    private val mainViewModel : MainViewModel by activityViewModels()
    private lateinit var binding: FragmentInsertLanguageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInsertLanguageBinding.inflate(inflater, container, false)

        textChangeListener()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = mainViewModel
            insertLanguageFragment = this@InsertLanguageFragment
        }

        binding.buttonInsertLanguageConfirm.setOnClickListener {
            if (validateOnConfirm()){
                mainViewModel.onInsertLanguageButtonClick()
                activity?.onBackPressed()
            }
        }
    }

    private fun textChangeListener() {
        binding.insertLanguageName.doOnTextChanged { _, _, _, _ ->
            binding.insertLanguageNameContainer.helperText = validateName()
        }
    }

    private fun validateName(): String?{
        val name = binding.insertLanguageName.text.toString()
        if (name == ""){
            return "Required"
        }
        return null
    }

    private fun validateOnConfirm(): Boolean{
        binding.insertLanguageNameContainer.helperText = validateName()

        if (binding.insertLanguageNameContainer.helperText == null) {
            return true
        }
        return false
    }
}