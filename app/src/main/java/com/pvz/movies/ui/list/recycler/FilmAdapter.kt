package com.pvz.movies.ui.list.recycler

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.pvz.movies.R
import com.pvz.movies.databinding.ItemMovieBinding
import com.pvz.movies.model.data.Film

class FilmAdapter(val clickListener: FilmItemOnClickListener) : ListAdapter<Film, FilmAdapter.FilmViewHolder>(FilmDiffCallback()) {
    class FilmViewHolder(private val binding: ItemMovieBinding, private val itemLickListener:FilmItemOnClickListener) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Film) {
            binding.apply {
                textView.text = item.name
                root.setOnClickListener {
                    itemLickListener.onClick(item.id)
                }
                loadImageUrl(item)

            }
        }

        private fun ItemMovieBinding.loadImageUrl(item: Film) {
            Glide.with(binding.root)
                .load(item.imageUrl)
                .addListener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        notFound.visibility = View.VISIBLE
                        return true
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        notFound.visibility = View.GONE
                        imageView.setImageDrawable(resource)
                        return true
                    }

                })
                .error(R.drawable.item_film_fallback)
                .into(imageView)
        }
    }

    class FilmDiffCallback : DiffUtil.ItemCallback<Film>() {
        override fun areItemsTheSame(oldItem: Film, newItem: Film): Boolean = oldItem == newItem

        override fun areContentsTheSame(oldItem: Film, newItem: Film): Boolean =
            oldItem.id == newItem.id

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context))
        return FilmViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}