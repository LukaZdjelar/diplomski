package com.example.diplomski_android.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.diplomski_android.R
import com.example.diplomski_android.model.Lesson
import com.example.diplomski_android.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.item_course.view.*
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
            tvLessonName.text = lesson.lessonType

            setOnClickListener{
                mainViewModel.setLesson(lesson)
                Toast.makeText(context, mainViewModel.lesson.value?.id.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}