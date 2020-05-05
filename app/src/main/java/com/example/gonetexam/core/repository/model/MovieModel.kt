package com.example.gonetexam.core.repository.model

class MovieModel (
    val iconMovie : String?,
    val movieName: String,
    val movieRated: Double,
    val movieLanguage: String,
    val movieRelease: String?
){
    enum class Type(val type: String) {
        POPULAR("popular"),
        TOP_RATED("top_rated"),
        UPCOMING("upcoming")
    }
}
