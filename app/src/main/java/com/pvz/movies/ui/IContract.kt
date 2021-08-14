package com.pvz.movies.ui

import androidx.lifecycle.LifecycleOwner


interface IContract {
    interface IPresenter<T>{
        fun takeView(view: T)

        fun dropView()
    }
    interface IView<T>:LifecycleOwner{

    }
}