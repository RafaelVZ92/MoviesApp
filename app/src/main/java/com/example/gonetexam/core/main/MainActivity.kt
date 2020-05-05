package com.example.gonetexam.core.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.gonetexam.R
import com.example.gonetexam.core.search.SearchActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setListener()
    }

    private fun setListener(){
        val intent = Intent(this, SearchActivity::class.java)
        cv_movies.setOnClickListener {
            startActivity(intent)
        }
        cv_series.setOnClickListener {
            startActivity(intent)
        }
    }
}
