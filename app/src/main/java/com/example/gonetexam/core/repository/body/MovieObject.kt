package com.example.gonetexam.core.repository.body

import com.google.gson.annotations.SerializedName

class MovieObject (
    @SerializedName("popularity")
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("original_language")
    val originalLanguage: String?,
    @SerializedName("title")
    val title: String,
    @SerializedName("release_date")
    val releaseDate: String ?,
    @SerializedName("adult")
    var isAdult:Boolean?
)
