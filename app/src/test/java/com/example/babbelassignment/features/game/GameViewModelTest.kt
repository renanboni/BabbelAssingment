package com.example.babbelassignment.features.game

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.babbelassignment.domain.WordManager
import com.example.babbelassignment.domain.entity.Word
import com.example.babbelassignment.domain.usecases.GetHighScore
import com.example.babbelassignment.domain.usecases.GetWords
import com.example.babbelassignment.domain.usecases.SaveHighScore
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class GameViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val getWords: GetWords = mock()
    private val manager: WordManager = mock()
    private val saveHighScore: SaveHighScore = mock()
    private val getHighScore: GetHighScore = mock()

    private val errorObserver: Observer<Unit> = mock()
    private val wordObserver: Observer<Word> = mock()
    private val scoreObserver: Observer<String> = mock()
    private val highScoreObserver: Observer<String> = mock()
    private val gameStateObserver: Observer<GameViewModel.GameState> = mock()
    private val loadingObserver: Observer<Boolean> = mock()

    private lateinit var viewModel: GameViewModel

    @Before
    fun setup() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

        viewModel = GameViewModel(getWords, manager, saveHighScore, getHighScore)
        viewModel.currentScore = 100

        viewModel.errorViewState.observeForever(errorObserver)
        viewModel.wordStateView.observeForever(wordObserver)
        viewModel.scoreViewState.observeForever(scoreObserver)
        viewModel.highScoreViewState.observeForever(highScoreObserver)
        viewModel.gameStateView.observeForever(gameStateObserver)
        viewModel.loadingViewState.observeForever(loadingObserver)
    }

    @Test
    fun `WHEN getWords is called THEN viewModel SHOULD update UI states`() {
        whenever(getWords()).thenReturn(Observable.just(listOf(Word("hi", "hola"))))
        whenever(manager.hasNext()).thenReturn(true)

        viewModel.getWords()

        // Displaying loading
        argumentCaptor<Boolean>().run {
            verify(loadingObserver, times(2)).onChanged(capture())

            assertEquals(true, firstValue)
            assertEquals(false, secondValue)
        }

        // GameState = Started
        argumentCaptor<GameViewModel.GameState>().run {
            verify(gameStateObserver).onChanged(capture())

            assertEquals(GameViewModel.GameState.Started, firstValue)
        }

        // Current score = 100
        argumentCaptor<String>().run {
            verify(scoreObserver).onChanged(capture())

            assertEquals("100", firstValue)
        }
    }

    @Test
    fun `WHEN getWords returns an error THEN viewModel SHOULD emit error`() {
        whenever(getWords()).thenReturn(Observable.error(Throwable()))

        viewModel.getWords()

        argumentCaptor<Unit>().run {
            verify(errorObserver).onChanged(capture())
        }

        argumentCaptor<Boolean>().run {
            verify(loadingObserver, times(2)).onChanged(capture())

            assertEquals(true, firstValue)
            assertEquals(false, secondValue)
        }
    }

    @Test
    fun `WHEN onCorrectClicked THEN viewModel SHOULD increment score, set next word and save current highscore`() {
        whenever(manager.isCorrectWord()).thenReturn(true)
        whenever(manager.hasNext()).thenReturn(true)

        viewModel.onCorrectClicked()

        argumentCaptor<String>().run {
            verify(scoreObserver).onChanged(capture())

            assertEquals("110", firstValue)
        }

        verify(saveHighScore).invoke(110)
    }

    @Test
    fun `WHEN onCorrectClicked but it was wrong THEN viewModel SHOULD decrement score, set next word and save current highscore`() {
        whenever(manager.isCorrectWord()).thenReturn(false)
        whenever(manager.hasNext()).thenReturn(true)

        viewModel.onCorrectClicked()

        argumentCaptor<String>().run {
            verify(scoreObserver).onChanged(capture())

            assertEquals("90", firstValue)
        }

        verify(saveHighScore).invoke(90)
    }

    @Test
    fun `WHEN onWrongClicked THEN viewModel SHOULD increment score, set next word and save current highscore`() {
        whenever(manager.isCorrectWord()).thenReturn(false)
        whenever(manager.hasNext()).thenReturn(true)

        viewModel.onWrongClicked()

        argumentCaptor<String>().run {
            verify(scoreObserver).onChanged(capture())

            assertEquals("110", firstValue)
        }

        verify(saveHighScore).invoke(110)
    }

    @Test
    fun `WHEN onWrongClicked but it was correct THEN viewModel SHOULD decrement score, set next word and save current highscore`() {
        whenever(manager.isCorrectWord()).thenReturn(true)
        whenever(manager.hasNext()).thenReturn(true)

        viewModel.onWrongClicked()

        argumentCaptor<String>().run {
            verify(scoreObserver).onChanged(capture())

            assertEquals("90", firstValue)
        }

        verify(saveHighScore).invoke(90)
    }

    @Test
    fun `WHEN onNoAnswer is called THEN viewModel SHOULD decrement score, set next word and save current highscore`() {
        whenever(manager.hasNext()).thenReturn(true)

        viewModel.onNoAnswer()

        argumentCaptor<String>().run {
            verify(scoreObserver).onChanged(capture())

            assertEquals("90", firstValue)
        }

        verify(saveHighScore).invoke(90)
    }

    @Test
    fun `WHEN decrement value is called but score is lower than 0 THEN viewModel SHOULD not decrement value`() {
        viewModel.currentScore = 0

        viewModel.onNoAnswer()

        assertEquals(0, viewModel.currentScore)
    }

    @Test
    fun `WHEN onCorrectClicked is called on the last item THEN viewModel SHOULD emit score and end the game`() {
        whenever(manager.isCorrectWord()).thenReturn(true)
        whenever(manager.hasNext()).thenReturn(false)

        viewModel.onCorrectClicked()

        argumentCaptor<String>().run {
            verify(scoreObserver).onChanged(capture())

            assertEquals("110", firstValue)
        }

        argumentCaptor<GameViewModel.GameState>().run {
            verify(gameStateObserver).onChanged(capture())

            assertEquals(GameViewModel.GameState.GameOver("110"), firstValue)
        }

        assertEquals(0, viewModel.currentScore)

        verify(saveHighScore).invoke(110)
        verify(manager).reset()
    }
}