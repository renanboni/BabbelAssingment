package com.example.babbelassignment.domain

import com.example.babbelassignment.domain.entity.Word
import javax.inject.Inject

class WordManager @Inject constructor(
    private val randomAnswer: RandomAnswer
) {
    var words = mutableListOf<Word>()

    internal lateinit var currentWord: Word

    internal var currentIndex = 0

    internal var isCorrect = false

    fun hasNext() = currentIndex < words.size

    fun nextWord(): Word {
        currentWord = words[currentIndex]

        return if (randomAnswer.shouldGetWrongAnswer()) {
            val random = randomAnswer.generateRandomIndex(currentIndex, words.count())

            if (random == currentIndex) {
                isCorrect = true
            } else {
                isCorrect = false
                currentWord.spanish = words[random].spanish
            }

            currentIndex++
            currentWord
        } else {
            isCorrect = true
            currentIndex++
            currentWord
        }
    }

    fun reset() {
        currentIndex = 0
    }

    fun isCorrectWord(): Boolean {
        return isCorrect
    }
}