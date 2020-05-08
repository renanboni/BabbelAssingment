package com.example.babbelassignment.domain.usecases

import com.example.babbelassignment.TestSchedulerProvider
import com.example.babbelassignment.domain.entity.Word
import com.example.babbelassignment.domain.repository.WordsRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Test

class GetWordsTest {

    private val repository: WordsRepository = mock()

    private val getWords = GetWords(repository, TestSchedulerProvider())

    @Test
    fun `WHEN getWords is called THEN it should complete`() {
        whenever(repository.getWords()).thenReturn(Observable.just(listOf(getWord())))

        getWords()
            .test()
            .assertComplete()
    }

    @Test
    fun `WHEN getWords is called THEN it should return a list of words`() {
        val words = listOf(getWord())
        whenever(repository.getWords()).thenReturn(Observable.just(words))

        getWords()
            .test()
            .assertValue(words)
    }

    private fun getWord(): Word {
        return Word(
            english = "hi",
            spanish = "hola"
        )
    }
}