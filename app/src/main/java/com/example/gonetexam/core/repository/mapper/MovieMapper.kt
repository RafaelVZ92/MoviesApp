package com.example.gonetexam.core.repository.mapper

import com.example.gonetexam.core.repository.model.MovieModel

class MovieMapper(private val movieList: MutableList<Any?>) : Mapper<List<Any?>?, MutableList<Any?>>{
    override fun map(input: List<Any?>?): MutableList<Any?> {
        val mutableList = mutableListOf<Any?>()

        if (input.isNullOrEmpty() && movieList.isEmpty()) {
            mutableList.add(Any())
            return mutableList
        } else if (input.isNullOrEmpty() && movieList.isNotEmpty()) {
            return mutableList
        }
        val items = mutableListOf<MovieModel>()
        input?.let {
            val list = input.filterIsInstance<MovieModel>()
            items.addAll(list)
        }
       input?.forEach {
            when(it){
                is MovieModel -> mutableList.add(it)
                else -> mutableList.add(it)
            }

        }
        return mutableList
    }
}