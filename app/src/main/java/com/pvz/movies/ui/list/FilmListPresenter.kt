package com.pvz.movies.ui.list

import android.util.Log
import com.pvz.movies.model.repository.FilmDataSource
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class FilmListPresenter @Inject constructor(val repository:FilmDataSource):FilmListContract.FilmListPresenter {
  var view: FilmListContract.FilmListView?=null

    override fun takeView(view: FilmListContract.FilmListView) {
        this.view=view

        CoroutineScope(Dispatchers.IO).launch{
            var list = repository.fetchFilmList()
            Log.d("test","$list")
        }

    }

    override fun dropView() {
        this.view=null
    }


}