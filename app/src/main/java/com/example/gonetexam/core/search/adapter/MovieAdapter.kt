package com.example.gonetexam.core.search.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gonetexam.R
import com.example.gonetexam.core.base.ItemListener
import com.example.gonetexam.core.repository.model.MovieModel

class MovieAdapter(private val items: MutableList<Any?>,
                   internal var itemClick: ItemListener?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var context: Context? = null

    fun itemClick(index: Int) {
//        val transfer: MovementModel = items[index] as MovementModel
//        this.selection?.invoke(index, transfer.id)
    }



    override fun getItemCount(): Int {
        return  items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is MovieHolder -> {
                val item = items[position] as MovieModel
                holder.iconMovie.setImageResource(R.drawable.ic_launcher_background)
                holder.movieName.text = item.movieName
                holder.movieRated.text = item.movieRated.toString()
                holder.movieLanguage.text = item.movieLanguage
                holder.movieRelease.text = item.movieRelease
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is MovieModel -> ITEM
            is Any -> PLACE_HOLDER
            else -> LOADER
        }
    }

    companion object {
        private const val ITEM = R.layout.card_movie_info
        private const val PLACE_HOLDER = R.layout.not_found_data
        private const val LOADER = R.layout.cv_item_loader
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        return when(viewType){
            ITEM -> MovieHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.card_movie_info, parent, false), this)

            PLACE_HOLDER -> PlaceHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.not_found_data, parent, false)
            )
            else -> LoaderHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.cv_item_loader, parent, false))
        }
    }
}

class MovieHolder(itemView: View, private val movieAdapter: MovieAdapter) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    val iconMovie : ImageView = itemView.findViewById(R.id.entertainment_logo)
    val movieName: TextView = itemView.findViewById(R.id.tv_movie_name)
    val movieRated: TextView = itemView.findViewById(R.id.tv_movie_rated)
    val movieLanguage: TextView = itemView.findViewById(R.id.tv_movie_language)
    val movieRelease: TextView = itemView.findViewById(R.id.tv_movie_release)
    init {
        itemView.setOnClickListener(this)
    }
    override fun onClick(v: View?) = movieAdapter.itemClick(adapterPosition)
}

class PlaceHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class LoaderHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

