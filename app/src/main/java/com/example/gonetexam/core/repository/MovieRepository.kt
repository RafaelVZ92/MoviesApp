package com.example.gonetexam.core.repository


import com.example.gonetexam.api.Resource
import com.example.gonetexam.common.utils.LANGUAGE
import com.example.gonetexam.core.base.BaseRepository
import com.example.gonetexam.core.repository.mapper.MovieModelMapper
import com.example.gonetexam.core.repository.model.MovieModel

class MovieRepository : BaseRepository() {

    suspend fun getMovie(type: MovieModel.Type, page: Int): Resource<List<MovieModel>> {
        return try {
            val movie = service.getMovie(
                type.name.toLowerCase(),
                apiKey,
                LANGUAGE,
                page
            )
            if (!movie.isSuccessful) {
                return responseHandler.handleException(movie.headers())
            }
            getPoster()
            return responseHandler.handleSuccess(
                MovieModelMapper().map(movie.body()!!)
            )
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

    suspend fun getPoster(): Resource<Unit>{
        return try {
            val movie = loadImage.getPoster(
                "xBHvZcjRiWyobQ9kxBhO6B2dtRI.jpg"
            )
            if (!movie.isSuccessful) {
                return responseHandler.handleException(movie.headers())
            }
            return responseHandler.handleSuccess(
               movie.body()!!
            )
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }
}