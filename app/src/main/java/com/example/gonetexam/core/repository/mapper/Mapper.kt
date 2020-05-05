package com.example.gonetexam.core.repository.mapper

interface Mapper<I, O> {
    fun map(input: I): O
}
