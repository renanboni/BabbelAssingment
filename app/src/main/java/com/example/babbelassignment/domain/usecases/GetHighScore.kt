package com.example.babbelassignment.domain.usecases

import com.example.babbelassignment.domain.repository.ScoreRepository
import javax.inject.Inject

class GetHighScore @Inject constructor(
    private val scoreRepository: ScoreRepository
) {
    operator fun invoke(): Int {
        return scoreRepository.getHighScore()
    }
}