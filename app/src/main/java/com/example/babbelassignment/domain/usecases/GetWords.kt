package com.example.babbelassignment.domain.usecases

import com.example.babbelassignment.domain.entity.Word
import com.example.babbelassignment.domain.repository.WordsRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetWords @Inject constructor(
    private val wordsRepository: WordsRepository
) {
    operator fun invoke(): Observable<List<Word>> {
        return wordsRepository.getWords()
    }
}