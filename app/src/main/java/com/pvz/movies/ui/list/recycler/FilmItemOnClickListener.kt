package com.pvz.movies.ui.list.recycler

import com.pvz.movies.model.data.Film

fun interface FilmItemOnClickListener {
    fun onClick(item:Film)
}