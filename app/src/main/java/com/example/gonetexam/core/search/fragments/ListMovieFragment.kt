package com.example.gonetexam.core.search.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gonetexam.R
import com.example.gonetexam.api.Resource
import com.example.gonetexam.api.Status
import com.example.gonetexam.core.repository.mapper.MovieMapper
import com.example.gonetexam.core.repository.model.MovieModel
import com.example.gonetexam.core.search.adapter.MovieAdapter
import com.example.gonetexam.core.search.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.fragment_list_movie.*

/**
 * A simple [Fragment] subclass.
 */
class ListMovieFragment : Fragment() {

    private val mMovieList: MutableList<Any?> = mutableListOf()
    private var readyToRefresh = true
    private var lastIndex = 1
    private val navigation by lazy { findNavController() }

    private val mMovieViewModel: SearchViewModel by lazy {
        ViewModelProvider(this).get(SearchViewModel::class.java)
    }

    private val mMovieObserver = Observer<Resource<List<MovieModel>>> {
        when (it.status) {
            Status.SUCCESS -> {
                readyToRefresh = (it.data != null && it.data.isNotEmpty() && it.data.size >= 20)
                lastIndex += it.data?.size?:0
                mMovieList.remove(null)
                movieRecycler.adapter?.notifyItemRemoved(mMovieList.lastIndex)
                mMovieList.addAll(MovieMapper(mMovieList).map(it.data))
                movieRecycler.adapter?.notifyDataSetChanged()
            }
            Status.ERROR -> {
                mMovieList.remove(null)
                movieRecycler.adapter?.notifyItemRemoved(mMovieList.lastIndex)
                mMovieList.addAll(MovieMapper(mMovieList).map(null))
                movieRecycler.adapter?.notifyDataSetChanged()
            }
            Status.LOADING -> {
                mMovieList.add(null)
                movieRecycler.adapter?.notifyItemInserted(mMovieList.lastIndex)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()
        movieRecycler.apply {
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(context)
            adapter = MovieAdapter(mMovieList){
                    index, id ->
                navigation.navigate(R.id.action_listMovieFragment_to_detailMovieFragment)
            }
            addOnScrollListener(retrieveScrollListener(layoutManager as LinearLayoutManager))
        }
        if (mMovieList.isEmpty()) {
            mMovieViewModel.getMovie(MovieModel.Type.POPULAR, 1)
        }
        retrieveResponseServices()
    }

    private fun initComponents(){
        val category = resources.getStringArray(R.array.category)
        val adapter = ArrayAdapter(requireContext(),
            android.R.layout.simple_spinner_dropdown_item, category)
        sp_category.adapter = adapter

        btn_search.setOnClickListener {
            when(sp_category.selectedItem.toString()){
                    "Popular" -> {
                        mMovieViewModel.getMovie(MovieModel.Type.POPULAR, 1)
                    }
                    "Top Rated" -> {
                        mMovieViewModel.getMovie(MovieModel.Type.TOP_RATED, 1)
                    }
                    "Upcoming" -> {
                        mMovieViewModel.getMovie(MovieModel.Type.UPCOMING, 1)
                    }
            }
        }
    }

    private fun retrieveResponseServices() {
        mMovieViewModel.mListMovies.observe(viewLifecycleOwner, mMovieObserver)
    }

    private fun retrieveScrollListener(layoutManager: LinearLayoutManager): RecyclerView.OnScrollListener {
        return object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()
                    if (readyToRefresh) {
                        if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                            readyToRefresh = false
                            lastIndex + 1
                            when(sp_category.selectedItem.toString()){
                                "Popular" -> mMovieViewModel.getMovie(MovieModel.Type.POPULAR, lastIndex)
                                "Top Rated" -> mMovieViewModel.getMovie(MovieModel.Type.TOP_RATED, lastIndex)
                                "Upcoming" -> mMovieViewModel.getMovie(MovieModel.Type.UPCOMING, lastIndex)
                            }

                        }
                    }
                }
            }
        }
    }
}
