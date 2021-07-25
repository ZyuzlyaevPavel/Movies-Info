package com.pvz.movies.ui.list.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pvz.movies.databinding.ItemGenreBinding
import com.pvz.movies.model.data.Genre

class GenreAdapter(private val onClickListener: GenreItemOnClickListener) :
    ListAdapter<Genre, GenreAdapter.GenreViewHolder>(GenreDiffCallback()) {
    var selectedIndex = -1
    var lastItemSelectedPosition = -1

    inner class GenreViewHolder(
        private val binding: ItemGenreBinding,
        val clickListener: GenreItemOnClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Genre, position: Int) {
            binding.apply {
                genreName.apply {
                    text = item.name
                    setOnClickListener {
                        clickListener.onClick(item, position)
                        if (selectedIndex != -1)
                            notifyItemChanged(lastItemSelectedPosition)
                        lastItemSelectedPosition = position
                        selectedIndex = position
                        notifyItemChanged(position)
                    }
                    isSelected = selectedIndex == position
                }
            }
        }
    }

    class GenreDiffCallback : DiffUtil.ItemCallback<Genre>() {
        override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean = oldItem == newItem

        override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean =
            oldItem.name == newItem.name

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val binding = ItemGenreBinding.inflate(LayoutInflater.from(parent.context))

        return GenreViewHolder(binding, onClickListener)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

}