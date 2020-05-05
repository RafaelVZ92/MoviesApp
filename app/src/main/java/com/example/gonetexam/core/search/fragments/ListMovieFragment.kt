package com.example.gonetexam.core.search.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.gonetexam.R
import com.example.gonetexam.api.Resource
import com.example.gonetexam.api.Status
import com.example.gonetexam.core.base.BaseActivity
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

    private val mMovieViewModel: SearchViewModel by lazy {
        ViewModelProvider(this).get(SearchViewModel::class.java)
    }

    private val mMovieObserver = Observer<Resource<List<MovieModel>>> {
        when (it.status) {
            Status.SUCCESS -> {
                //(requireActivity() as BaseActivity).hideProgressLoader()
                mMovieList.remove(null)
                movieRecycler.adapter?.notifyItemRemoved(mMovieList.lastIndex)
                mMovieList.addAll(MovieMapper(mMovieList).map(it.data))
                movieRecycler.adapter?.notifyDataSetChanged()
            }
            Status.ERROR -> {
                //(requireActivity() as BaseActivity).hideProgressLoader()
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
                mMovieViewModel.mListMovies.value = null
            }
        }
        if (mMovieList.isEmpty()) {
            mMovieViewModel.getMovie(MovieModel.Type.POPULAR, 1)
            //(requireActivity() as BaseActivity).showProgressLoader()
        }
        retrieveResponseServices()
    }

    private fun initComponents(){
        val category = resources.getStringArray(R.array.category)
        val adapter = ArrayAdapter(requireContext(),
            android.R.layout.simple_spinner_dropdown_item, category)
        sp_category.adapter = adapter
    }

    private fun retrieveResponseServices() {
        mMovieViewModel.mListMovies.observe(viewLifecycleOwner, mMovieObserver)
    }
}
