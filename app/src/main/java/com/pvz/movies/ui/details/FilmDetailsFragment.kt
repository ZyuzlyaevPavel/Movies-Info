package com.pvz.movies.ui.details

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.pvz.movies.R
import com.pvz.movies.databinding.FragmentDetailsBinding
import com.pvz.movies.model.data.Film
import com.pvz.movies.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FilmDetailsFragment : Fragment(R.layout.fragment_details),
    FilmDetailsContract.FilmDetailsView {
    @Inject
    lateinit var presenter: FilmDetailsContract.FilmDetailsPresenter
    private lateinit var binding: FragmentDetailsBinding
    private val args: FilmDetailsFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        if (view != null)
            binding = FragmentDetailsBinding.bind(view)
        presenter.takeView(this)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        args.apply {
            if (!filmName.isNullOrEmpty())
                (activity as MainActivity).setActionBarTitle(filmName!!)
        }
        presenter.acquireFilmData(args.filmId)
        binding.apply {
        }
    }

    override fun onStart() {
        super.onStart()

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dropView()
    }

    override fun updateFilmDetails(film: Film) {

        binding.apply {
            filmName.text = film.name
            filmYear.text = "Год: ${film.year}"
            filmRating.text = "Рейтинг: ${film.rating}"
            description.text = film.description
            loadImageUrl(film.imageUrl, filmPoster, notFound)
        }

    }

    private fun loadImageUrl(imageUrl: String, imageView: ImageView, notFound: TextView) {
        CoroutineScope(Dispatchers.Default).launch {
            Glide.with(requireContext())
                .asBitmap()
                .load(imageUrl)
                .into(object : CustomTarget<Bitmap>(){
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

}