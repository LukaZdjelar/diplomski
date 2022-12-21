package com.example.diplomski_android.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.diplomski_android.R
import com.example.diplomski_android.databinding.ItemCourseBinding
import com.example.diplomski_android.model.Course
import com.example.diplomski_android.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CoursesAdapter(private val mainViewModel: MainViewModel) : RecyclerView.Adapter<CoursesAdapter.CoursesViewHolder>() {

    inner class CoursesViewHolder(val binding: ItemCourseBinding): RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Course>() {
        override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoursesViewHolder {
        return CoursesViewHolder(ItemCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CoursesViewHolder, position: Int) {
        val course = differ.currentList[position]
        holder.itemView.apply {
            holder.binding.llCourseItemAdmin.isVisible = mainViewModel.user.value?.admin == true
            holder.binding.tvCourseName.text = course.name

            setOnClickListener{
                mainViewModel.setCurrentCourse(course)
                Navigation.findNavController(holder.itemView).navigate(R.id.action_coursesFragment_to_chaptersFragment)
            }
            holder.binding.buttonEditCourse.setOnClickListener{
                mainViewModel.setNewCourse(course)
                Navigation.findNavController(holder.itemView).navigate(R.id.action_coursesFragment_to_insertCourseFragment)
            }
            holder.binding.buttonDeleteCourse.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    mainViewModel.deleteCourseComplete(course)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}