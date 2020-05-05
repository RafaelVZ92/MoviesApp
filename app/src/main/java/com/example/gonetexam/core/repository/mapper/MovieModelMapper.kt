package com.example.gonetexam.core.repository.mapper

import com.example.gonetexam.core.repository.body.MovieBody
import com.example.gonetexam.core.repository.model.MovieModel

class MovieModelMapper : Mapper<MovieBody, List<MovieModel>>{
    override fun map(input: MovieBody):  List<MovieModel>{
        val movies: MutableList<MovieModel> = arrayListOf()
        for (movie in input.results){
            movies.add(MovieModel(
                iconMovie = movie.posterPath,
                movieName = movie.title,
                movieRated = movie.popularity,
                movieLanguage = movie.originalLanguage?: "US",
                movieRelease = movie.releaseDate
                ))
        }
        return movies
    }
}