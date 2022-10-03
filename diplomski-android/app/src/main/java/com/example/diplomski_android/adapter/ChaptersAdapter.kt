package com.example.diplomski_android.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import com.example.diplomski_android.R
import com.example.diplomski_android.model.Chapter
import com.example.diplomski_android.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.item_chapter.view.*


class ChaptersAdapter(private val mainViewModel: MainViewModel): RecyclerView.Adapter<ChaptersAdapter.ChaptersViewHolder>() {

    private lateinit var view: View
    private val viewPool = RecycledViewPool()
    inner class ChaptersViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Chapter>() {
        override fun areItemsTheSame(oldItem: Chapter, newItem: Chapter): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Chapter, newItem: Chapter): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChaptersViewHolder {
        view = LayoutInflater.from(parent.context).inflate(R.layout.item_chapter, parent, false)
        return ChaptersViewHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: ChaptersViewHolder, position: Int) {
        val chapter = differ.currentList[position]
        holder.itemView.apply {
            tvChapterName.text = chapter.name
        }

        // Create layout manager with initial prefetch item count
        val layoutManager = LinearLayoutManager(
            holder.itemView.rvLessons.context,
            LinearLayoutManager.VERTICAL,
            false
        )
        layoutManager.initialPrefetchItemCount = chapter.lessons.size

        // Create sub item view adapter
        val lessonsAdapter = LessonsAdapter(mainViewModel)
        lessonsAdapter.differ.submitList(chapter.lessons)

        holder.itemView.rvLessons.layoutManager = layoutManager
        holder.itemView.rvLessons.adapter = lessonsAdapter
        holder.itemView.rvLessons.setRecycledViewPool(viewPool)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}