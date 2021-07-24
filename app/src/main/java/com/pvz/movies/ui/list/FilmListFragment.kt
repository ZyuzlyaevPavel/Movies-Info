package com.pvz.movies.ui.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.pvz.movies.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FilmListFragment: Fragment(R.layout.fragment_list),FilmListContract.FilmListView {
    @Inject
    lateinit var presenter:FilmListContract.FilmListPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        presenter.takeView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dropView()
    }
}