package com.pvz.movies.ui


interface IContract {
    interface IPresenter<T>{
        fun takeView(view: T)

        fun dropView()
    }
    interface IView<T>{

    }
}