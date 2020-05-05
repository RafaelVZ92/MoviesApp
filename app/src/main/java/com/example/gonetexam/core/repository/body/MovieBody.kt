package com.example.gonetexam.core.repository.body

class MovieBody (
        val id: Int = 0,
        val page: Int,
        val totalResults: Int,
        val totalPages: Int,
        val results: List<MovieObject>
)