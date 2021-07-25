package com.pvz.movies.ui.info

import com.pvz.movies.model.repository.FilmRepository
import javax.inject.Inject

class FilmDetailsPresenter @Inject constructor(private val repository: FilmRepository) :
    FilmDetailsContract.FilmDetailsPresenter {
    var view: FilmDetailsContract.FilmDetailsView? = null

    init {
    }

    override fun takeView(view: FilmDetailsContract.FilmDetailsView) {
        this.view = view


    }



    override fun dropView() {
        this.view = null

    }


}