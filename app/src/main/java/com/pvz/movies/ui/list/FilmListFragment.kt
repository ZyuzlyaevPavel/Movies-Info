package com.pvz.movies.ui.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexboxLayoutManager
import com.pvz.movies.R
import com.pvz.movies.databinding.FragmentListBinding
import com.pvz.movies.model.data.Film
import com.pvz.movies.model.data.Genre
import com.pvz.movies.ui.list.recycler.FilmAdapter
import com.pvz.movies.ui.list.recycler.GenreAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FilmListFragment : Fragment(R.layout.fragment_list), FilmListContract.FilmListView {
    @Inject
    lateinit var presenter: FilmListContract.FilmListPresenter
    private lateinit var binding: FragmentListBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        if (view != null)
            binding = FragmentListBinding.bind(view)
        return view
    }

    private var filmAdapter:FilmAdapter=FilmAdapter {
        Log.d("test","filmId -> $it")
        val action= FilmListFragmentDirections.actionFilmsListFragmentToFilmDetailsFragment(it.id,it.localizedName)
        findNavController().navigate(action)
    }
    private val selectedGenreList:MutableList<Genre> = mutableListOf()
    private var genreAdapter:GenreAdapter= GenreAdapter { item, _ ->
/*            if(!selectedGenreList.contains(item))
                selectedGenreList.add(item)
            else
                selectedGenreList.remove(item)*/

            presenter.filterFilmsByGenre(item)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            genresRecycler.layoutManager=FlexboxLayoutManager(context)
            genresRecycler.adapter=genreAdapter
            filmsRecycler.layoutManager=GridLayoutManager(context,2,RecyclerView.VERTICAL,false)
            filmsRecycler.adapter=filmAdapter
        }
        presenter.takeView(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.dropView()
    }



    override fun updateFilmRecycler(films: MutableList<Film>?) {
        Log.d("test","updateFilmRecycler")
        if (!films.isNullOrEmpty())
            films.sortBy { it.localizedName }
        filmAdapter.submitList(films)
    }

    override fun updateGenresRecycler(listOf: List<Genre>?) {
        Log.d("test","updateGenresRecycler")
        genreAdapter.submitList(listOf)
    }

    override fun notifyDataLoading() {
        with(binding) {
            notReadyLayout.visibility=View.VISIBLE
            readyLayout.visibility=View.GONE
            errorLayout.visibility=View.GONE
        }
    }

    override fun notifyDataAquisition() {
        with(binding) {
            notReadyLayout.visibility=View.GONE
            readyLayout.visibility=View.VISIBLE
            errorLayout.visibility=View.GONE
        }
    }

    override fun notifyDataLoadingFail() {
        with(binding) {
            notReadyLayout.visibility = View.GONE
            readyLayout.visibility = View.GONE
            errorLayout.visibility = View.VISIBLE
        }
    }

}