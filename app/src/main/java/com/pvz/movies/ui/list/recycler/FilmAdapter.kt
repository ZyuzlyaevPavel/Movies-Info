package com.pvz.movies.ui.list.recycler

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.pvz.movies.R
import com.pvz.movies.databinding.ItemMovieBinding
import com.pvz.movies.model.data.Film
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FilmAdapter(val clickListener: FilmItemOnClickListener) :
    ListAdapter<Film, FilmAdapter.FilmViewHolder>(FilmDiffCallback()) {
    class FilmViewHolder(
        private val binding: ItemMovieBinding,
        private val itemLickListener: FilmItemOnClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Film) {
            binding.apply {
                textView.text = item.localizedName
                root.setOnClickListener {
                    itemLickListener.onClick(item)
                }
                loadImageUrl(item)

            }
        }

        private fun ItemMovieBinding.loadImageUrl(item: Film) {
                Glide.with(binding.root)
                    .asBitmap()
                    .load(item.imageUrl)
                    .into(object : CustomTarget<Bitmap>() {
                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: Transition<in Bitmap>?
                        ) {
                            CoroutineScope(Dispatchers.Main).launch {
                                notFound.visibility = View.GONE
                                imageView.setImageBitmap(resource)
                            }
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {

                        }

                        override fun onLoadFailed(errorDrawable: Drawable?) {
                            super.onLoadFailed(errorDrawable)
                            CoroutineScope(Dispatchers.Main).launch {
                                notFound.visibility = View.VISIBLE
                                imageView.setImageResource(R.drawable.item_film_fallback)
                            }
                        }
                    })
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