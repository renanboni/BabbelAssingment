package com.example.babbelassignment.domain

import com.example.babbelassignment.domain.entity.Word
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class WordManagerTest {

    private val random: RandomAnswer = mock()

    private val manager = WordManager(random)

    @Before
    fun setup() {
        manager.words = mutableListOf(Word("hi", "hello"), Word("class", "curso"))
    }

    @Test
    fun `WHEN random generate a false value THEN manager SHOULD return the right answer`() {
        whenever(random.shouldGetWrongAnswer()).thenReturn(false)

        manager.nextWord()

        assertEquals(true, manager.isCorrect)
        assertEquals(1, manager.currentIndex)
        assertEquals(Word("hi", "hello"), manager.currentWord)
    }

    @Test
    fun `WHEN random generate a true value THEN manager SHOULD return the wrong answer`() {
        whenever(random.shouldGetWrongAnswer()).thenReturn(true)
        whenever(random.generateRandomIndex(any(), any())).thenReturn(1)

        manager.nextWord()

        assertEquals(false, manager.isCorrect)
        assertEquals(1, manager.currentIndex)
        assertEquals(Word("hi", "curso"), manager.currentWord)
    }


    @Test
    fun `WHEN random generate the same index THEN manager SHOULD return the right answer`() {
        whenever(random.shouldGetWrongAnswer()).thenReturn(true)
        whenever(random.generateRandomIndex(any(), any())).thenReturn(0)

        manager.nextWord()

        assertEquals(true, manager.isCorrect)
        assertEquals(1, manager.currentIndex)
        assertEquals(Word("hi", "hello"), manager.currentWord)
    }

    @Test
    fun `WHEN reset is called THEN manager SHOULD set current index to 0`() {
        manager.reset()

        assertEquals(0, manager.currentIndex)
    }
}