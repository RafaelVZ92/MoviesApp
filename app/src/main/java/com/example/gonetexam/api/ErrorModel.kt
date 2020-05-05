package com.example.gonetexam.api

class ErrorModel(val type: Type, val message: String) {

    enum class Type {
        FATAL,
        TOLERABLE
    }
}