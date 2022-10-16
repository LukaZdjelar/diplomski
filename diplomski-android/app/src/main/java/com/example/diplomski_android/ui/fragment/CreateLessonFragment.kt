package com.example.diplomski_android.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.diplomski_android.databinding.FragmentCreateLessonBinding
import com.example.diplomski_android.ui.viewmodel.MainViewModel

class CreateLessonFragment : Fragment() {

    private val mainViewModel : MainViewModel by activityViewModels()
    private var createLessonBinding: FragmentCreateLessonBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentCreateLessonBinding.inflate(inflater, container, false)
        createLessonBinding = fragmentBinding

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createLessonBinding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = mainViewModel
            createLessonFragment = this@CreateLessonFragment
        }
    }
}