package com.example.babbelassignment.features.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.babbelassignment.domain.WordManager
import com.example.babbelassignment.domain.entity.Word
import com.example.babbelassignment.domain.usecases.GetWords
import com.example.babbelassignment.domain.usecases.SaveHighScore
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class GameViewModel @Inject constructor(
    private val getWordsUseCase: GetWords,
    private val wordManager: WordManager,
    private val saveHighScore: SaveHighScore
) : ViewModel() {

    private val disposable = CompositeDisposable()

    private var currentScore = 0

    private val wordState = MutableLiveData<Word>()
    val wordStateView: LiveData<Word> = wordState

    private val scoreState = MutableLiveData<String>()
    val scoreViewState: LiveData<String> = scoreState

    private val loadingState = MutableLiveData<Boolean>()
    val loadingViewState: LiveData<Boolean> = loadingState

    private val errorState = MutableLiveData<Unit>()
    val errorViewState: LiveData<Unit> = errorState

    private val gameState = MutableLiveData<GameState>()
    val gameStateView: LiveData<GameState> = gameState

    private fun getNextWord() {
        if (wordManager.hasNext()) {
            wordState.value = wordManager.nextWord()
        } else {
            saveHighScore(currentScore)
            gameState.value = GameState.GameOver
        }
    }

    fun getWords() {
        disposable.add(getWordsUseCase()
            .doOnSubscribe { loadingState.value = true }
            .doOnComplete { loadingState.value = false }
            .subscribe ({
                wordManager.words = it.toMutableList()
                gameState.value = GameState.Started
                getNextWord()
            }, {
                errorState.value = Unit
            })
        )
    }

    fun onCorrectClicked() {
        if (wordManager.isCorrectWord()) {
            currentScore += 10
        } else {
            currentScore -= 10
        }

        publishScore()
    }

    fun onWrongClicked() {
        if (!wordManager.isCorrectWord()) {
            currentScore += 10
        } else {
            currentScore -= 10
        }

        publishScore()
    }

    fun onNoAnswer() {
        currentScore -= 10
        publishScore()
    }

    private fun publishScore() {
        scoreState.value = currentScore.toString()
        getNextWord()
    }

    sealed class GameState {
        object Started: GameState()
        object GameOver: GameState()
    }
}