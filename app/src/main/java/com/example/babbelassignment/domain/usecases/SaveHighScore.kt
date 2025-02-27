package com.example.babbelassignment.domain.usecases

import com.example.babbelassignment.domain.repository.ScoreRepository
import javax.inject.Inject

class SaveHighScore @Inject constructor(
    private val repository: ScoreRepository
) {
    operator fun invoke(score: Int) {
        val currentHighScore = repository.getHighScore()

        if (score > currentHighScore) {
            repository.setHighScore(score)
        }
    }
}