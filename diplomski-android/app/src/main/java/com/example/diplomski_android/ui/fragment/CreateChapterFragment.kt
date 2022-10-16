package com.example.diplomski_android.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.diplomski_android.databinding.FragmentCreateChapterBinding
import com.example.diplomski_android.ui.viewmodel.MainViewModel

class CreateChapterFragment : Fragment() {

    private val mainViewModel : MainViewModel by activityViewModels()
    private var createChapterBinding: FragmentCreateChapterBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentCreateChapterBinding.inflate(inflater, container, false)
        createChapterBinding = fragmentBinding

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createChapterBinding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = mainViewModel
            createChapterFragment = this@CreateChapterFragment
        }
    }
}