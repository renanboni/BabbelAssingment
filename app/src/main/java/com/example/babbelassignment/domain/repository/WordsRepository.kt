package com.example.babbelassignment.domain.repository

import com.example.babbelassignment.domain.entity.Word
import io.reactivex.Observable

interface WordsRepository {
    fun getWords(): Observable<List<Word>>
}