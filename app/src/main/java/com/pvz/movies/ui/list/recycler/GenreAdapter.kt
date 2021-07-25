package com.pvz.movies.ui.list.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pvz.movies.databinding.ItemGenreBinding
import com.pvz.movies.model.data.Genre

class GenreAdapter : ListAdapter<Genre, GenreAdapter.GenreViewHolder>(GenreDiffCallback()) {
    class GenreViewHolder(val binding: ItemGenreBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Genre) {
            binding.genreName.text = item.name
        }
    }

    class GenreDiffCallback : DiffUtil.ItemCallback<Genre>() {
        override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean = oldItem == newItem

        override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean =
            oldItem.name == newItem.name

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val binding = ItemGenreBinding.inflate(LayoutInflater.from(parent.context))
        return GenreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}