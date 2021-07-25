package com.pvz.movies.ui.info

import com.pvz.movies.ui.IContract

interface FilmDetailsContract:IContract {
    interface FilmDetailsPresenter: IContract.IPresenter<FilmDetailsView> {

    }
    interface FilmDetailsView:IContract.IView<FilmDetailsPresenter>{

    }
}