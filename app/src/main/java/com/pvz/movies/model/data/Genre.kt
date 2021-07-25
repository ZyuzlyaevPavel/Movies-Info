package com.pvz.movies.model.data

data class Genre(
    val name:String
) {

    override fun equals(other: Any?): Boolean =
        if(other is Genre)
            this.name==other.name
        else
            false
}