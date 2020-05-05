package com.example.gonetexam.core.search

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.gonetexam.R
import com.example.gonetexam.api.Resource
import com.example.gonetexam.api.Status
import com.example.gonetexam.core.base.BaseActivity
import com.example.gonetexam.core.repository.model.MovieModel
import com.example.gonetexam.core.search.viewmodel.SearchViewModel

class SearchActivity : BaseActivity() {

    private val mNavigation by lazy { findNavController(R.id.nav_controller_detail) }

    private val mMovieViewModel: SearchViewModel by lazy {
        ViewModelProvider(this).get(SearchViewModel::class.java)
    }

    private val mMovieObserver = Observer<Resource<List<MovieModel>>> {
        when (it.status) {
            Status.SUCCESS -> {
                hideProgressLoader()
            }
            Status.ERROR -> {
                hideProgressLoader()
            }
            Status.LOADING -> showProgressLoader()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        //mMovieViewModel.getMovie(MovieModel.Type.POPULAR, 1)
        //showProgressLoader()
        //retrieveResponseServices()
    }

    private fun retrieveResponseServices() {
        mMovieViewModel.mListMovies.observe(this, mMovieObserver)
    }
}
