package com.example.gonetexam.core.search.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.gonetexam.api.Resource
import com.example.gonetexam.core.base.BaseViewModel
import com.example.gonetexam.core.repository.MovieRepository
import com.example.gonetexam.core.repository.model.MovieModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchViewModel : BaseViewModel() {

    private val mRepository by lazy { MovieRepository() }

    val mListMovies = MutableLiveData<Resource<List<MovieModel>>>()

    fun getMovie(type: MovieModel.Type, page: Int) {
        mListMovies.value = Resource.loading(null)
        GlobalScope.launch {
            val movies = mRepository.getMovie(type, page)
            withContext(Dispatchers.Main) {
                mListMovies.value = movies
            }
        }
    }
}