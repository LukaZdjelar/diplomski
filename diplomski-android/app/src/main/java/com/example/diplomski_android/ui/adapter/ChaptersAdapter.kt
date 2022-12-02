package com.example.diplomski_android.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.diplomski_android.MainViewModel
import com.example.diplomski_android.R
import com.example.diplomski_android.databinding.ItemChapterBinding
import com.example.diplomski_android.model.Chapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ChaptersAdapter(private val mainViewModel: MainViewModel): RecyclerView.Adapter<ChaptersAdapter.ChaptersViewHolder>() {

    inner class ChaptersViewHolder(val binding: ItemChapterBinding): RecyclerView.ViewHolder(binding.root)

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
        return ChaptersViewHolder(ItemChapterBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ChaptersViewHolder, position: Int) {
        val chapter = differ.currentList[position]

        holder.itemView.apply {
            holder.binding.llChapterItemAdmin.isVisible = mainViewModel.user.value?.admin!!
            val progressString = "${chapter.completedLessons}/${chapter.totalLessons}"

            holder.binding.tvChapterName.text = chapter.name
            holder.binding.tvChapterDifficulty.text = chapter.difficulty
            holder.binding.tvChapterProgress.text = progressString

            setOnClickListener{
                mainViewModel.setCurrentChapter(chapter)
                Navigation.findNavController(holder.itemView).navigate(R.id.action_chaptersFragment_to_lessonsFragment)
            }

            holder.binding.buttonEditChapter.setOnClickListener{
                mainViewModel.setNewChapter(chapter)
                Navigation.findNavController(holder.itemView).navigate(R.id.action_chaptersFragment_to_insertChapterFragment)
            }

            holder.binding.buttonDeleteChapter.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    mainViewModel.deleteChapterComplete(chapter)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}