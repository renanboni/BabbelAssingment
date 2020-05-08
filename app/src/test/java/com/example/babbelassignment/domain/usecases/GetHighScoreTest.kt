package com.example.babbelassignment.domain.usecases

import com.example.babbelassignment.domain.repository.ScoreRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.assertEquals
import org.junit.Test

class GetHighScoreTest {

    private val repository: ScoreRepository = mock()

    private val getHighScore = GetHighScore(repository)

    @Test
    fun `WHEN getHighScore is invoked THEN it SHOULD return highScore from repository`() {
        whenever(repository.getHighScore()).thenReturn(10)

        val expected = getHighScore()

        assertEquals(expected, 10)
    }
}