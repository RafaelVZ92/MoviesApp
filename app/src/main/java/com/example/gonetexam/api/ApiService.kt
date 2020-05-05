package com.example.gonetexam.api

import android.util.Base64
import android.widget.ImageView
import com.example.gonetexam.core.repository.body.MovieBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {

    @GET
    suspend fun getMovie(
        @Url path: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<MovieBody>

    @GET
    suspend fun getPoster(
        @Url path: String
    ): Response<Unit>
}