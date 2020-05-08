package com.example.babbelassignment.domain.usecases

import com.example.babbelassignment.domain.repository.ScoreRepository
import com.nhaarman.mockitokotlin2.whenever
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.any
import org.junit.Test

class SaveHighScoreTest {

    private val repository: ScoreRepository = mock()

    private val saveHighScore = SaveHighScore(repository)

    @Test
    fun `GIVEN a score greater than the current hs THEN saveHighScore SHOULD update it`() {
        whenever(repository.getHighScore()).thenReturn(100)

        saveHighScore(150)

        verify(repository).setHighScore(150)
    }

    @Test
    fun `GIVEN a score lower than the current hs THEN saveHighScore SHOULD not update it`() {
        whenever(repository.getHighScore()).thenReturn(100)

        saveHighScore(90)

        verify(repository, times(0)).setHighScore(any())
    }
}