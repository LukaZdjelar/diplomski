package com.example.diplomski_android.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.diplomski_android.MainViewModel
import com.example.diplomski_android.R
import com.example.diplomski_android.model.Lesson
import kotlinx.android.synthetic.main.item_lesson.view.*

class LessonsAdapter(private val mainViewModel: MainViewModel): RecyclerView.Adapter<LessonsAdapter.LessonsViewHolder>() {

    private lateinit var view: View
    inner class LessonsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Lesson>() {
        override fun areItemsTheSame(oldItem: Lesson, newItem: Lesson): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Lesson, newItem: Lesson): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonsViewHolder {
        view = LayoutInflater.from(parent.context).inflate(R.layout.item_lesson, parent, false)
        return LessonsViewHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: LessonsViewHolder, position: Int) {
        val lesson = differ.currentList[position]
        holder.itemView.apply {
            tvLessonName.text = lesson.lesson_type

//          TODO: ne radi Toast
            setOnClickListener{
                if (lesson.tasks!!.isNotEmpty()){
                    mainViewModel.setTasks(lesson.tasks!!)
                    Navigation.findNavController(view).navigate(R.id.action_chaptersFragment_to_taskFragment)
                }else{
                    Toast.makeText(context,"There are no tasks", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}