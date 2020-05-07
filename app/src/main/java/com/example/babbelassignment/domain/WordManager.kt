package com.example.babbelassignment.domain

import com.example.babbelassignment.domain.entity.Word
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import kotlin.random.Random

class WordManager @Inject constructor() {

    private var subject = PublishSubject.create<Word>()

    var observable: Observable<Word> = subject

    var words = mutableListOf<Word>()

    private lateinit var currentWord: Word
    private var currentIndex = 0

    private var isCorrect = false

    fun hasNext() = currentIndex < words.size

    fun nextWord() {
        currentWord = words[currentIndex]

        if (shouldGetWrongAnswer()) {
            val random = Random.nextInt(currentIndex, words.count())
            currentWord.spanish = words[random].spanish
            isCorrect = false
            subject.onNext(currentWord)
        } else {
            isCorrect = true
            subject.onNext(currentWord)
        }

        currentIndex++
    }

    fun isCorrectWord(): Boolean {
        return isCorrect
    }

    private fun shouldGetWrongAnswer(): Boolean {
        return Random.nextBoolean()
    }
}