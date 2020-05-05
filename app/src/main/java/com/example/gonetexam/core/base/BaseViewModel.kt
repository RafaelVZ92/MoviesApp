package com.example.gonetexam.core.base

import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel(){
    private val repository by lazy { BaseRepository() }
}