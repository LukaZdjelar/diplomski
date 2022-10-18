package com.example.diplomski_android.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.diplomski_android.R
import com.example.diplomski_android.databinding.FragmentCreateChapterBinding
import com.example.diplomski_android.ui.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_create_chapter.*

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

        //TODO: Privremeno
        val items = listOf("Course 1", "Course 2", "Course 3", "Course 4")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        (menu_chapter_courses.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }
}