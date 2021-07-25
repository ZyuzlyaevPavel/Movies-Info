package com.pvz.movies.ui.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        Log.d("test","filmId -> $it")
        val action= FilmListFragmentDirections.actionFilmsListFragmentToFilmDetailsFragment(it.id,it.localizedName)
        findNavController().navigate(action)
    }
    private val selectedGenreList:MutableList<Genre> = mutableListOf()
    private var genreAdapter:GenreAdapter= GenreAdapter { item, position ->
/*            if(!selectedGenreList.contains(item))
                selectedGenreList.add(item)
            else
                selectedGenreList.remove(item)*/

            presenter.filterFilmsByGenre(item)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            genresRecycler.layoutManager=LinearLayoutManager(context)
            genresRecycler.adapter=genreAdapter
            filmsRecycler.layoutManager=GridLayoutManager(context,2,RecyclerView.VERTICAL,false)
            filmsRecycler.adapter=filmAdapter

        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.takeView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
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
}