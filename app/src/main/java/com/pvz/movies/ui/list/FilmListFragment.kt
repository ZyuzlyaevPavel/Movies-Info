package com.pvz.movies.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

    private var filmAdapter:FilmAdapter=FilmAdapter()
    private var genreAdapter:GenreAdapter= GenreAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            genresRecycler.layoutManager=LinearLayoutManager(context)
            genresRecycler.adapter=genreAdapter
            filmsRecycler.layoutManager=GridLayoutManager(context,2,RecyclerView.VERTICAL,false)
            //filmsRecycler.layoutManager=LinearLayoutManager(context)
            filmsRecycler.adapter=filmAdapter

        }
    }

    override fun onResume() {
        super.onResume()
        presenter.takeView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dropView()
    }

    override fun updateFilmRecycler(films: List<Film>?) {
        filmAdapter.submitList(films)
    }

    override fun updateGenresRecycler(listOf: List<Genre>) {
        genreAdapter.submitList(listOf)
    }
}