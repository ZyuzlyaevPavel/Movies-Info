package com.pvz.movies.model.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Genre(
    val name:String
): Parcelable {

    override fun equals(other: Any?): Boolean =
        if(other is Genre)
            this.name==other.name
        else
            false
}