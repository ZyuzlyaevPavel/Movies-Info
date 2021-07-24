package com.pvz.movies.ui.list

import com.pvz.movies.ui.IContract

interface FilmListContract:IContract {
    interface FilmListPresenter: IContract.IPresenter<FilmListView> {

    }
    interface FilmListView:IContract.IView<FilmListPresenter>{

    }
}