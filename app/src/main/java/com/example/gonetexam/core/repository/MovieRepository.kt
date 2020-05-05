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
                API_KEY,
                LANGUAGE,
                page
            )
            if (!movie.isSuccessful) {
                return responseHandler.handleException(movie.headers())
            }
            return responseHandler.handleSuccess(
                MovieModelMapper().map(movie.body()!!)
            )
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

}