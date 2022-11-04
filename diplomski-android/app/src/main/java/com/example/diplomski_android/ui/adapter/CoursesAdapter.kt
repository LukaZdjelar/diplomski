package com.example.diplomski_android.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.diplomski_android.MainViewModel
import com.example.diplomski_android.R
import com.example.diplomski_android.model.Course
import kotlinx.android.synthetic.main.fragment_insert_course.view.*
import kotlinx.android.synthetic.main.item_course.view.*
import kotlinx.coroutines.runBlocking

class CoursesAdapter(private val mainViewModel: MainViewModel) : RecyclerView.Adapter<CoursesAdapter.CoursesViewHolder>() {
    private lateinit var view: View

    inner class CoursesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

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
        view = LayoutInflater.from(parent.context).inflate(R.layout.item_course, parent, false)
        return CoursesViewHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: CoursesViewHolder, position: Int) {
        val course = differ.currentList[position]
        holder.itemView.apply {
            tvCourseName.text = course.name

            setOnClickListener{
                mainViewModel.setCourse(course)
                Navigation.findNavController(view).navigate(R.id.action_coursesFragment_to_chaptersFragment)
            }
            button_edit_course.setOnClickListener{
                mainViewModel.setNewCourse(course)
                Navigation.findNavController(view).navigate(R.id.action_coursesFragment_to_insertCourseFragment)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}