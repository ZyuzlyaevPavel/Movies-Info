package com.pvz.movies.ui.list.recycler

import com.pvz.movies.model.data.Genre

fun interface GenreItemOnClickListener {
    fun onClick(item: Genre, selectedIndex: Int)
}