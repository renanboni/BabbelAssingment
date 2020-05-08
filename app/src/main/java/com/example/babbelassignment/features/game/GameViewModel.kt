package com.example.babbelassignment.features.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.babbelassignment.core.base.BaseViewModel
import com.example.babbelassignment.domain.WordManager
import com.example.babbelassignment.domain.entity.Word
import com.example.babbelassignment.domain.usecases.GetHighScore
import com.example.babbelassignment.domain.usecases.GetWords
import com.example.babbelassignment.domain.usecases.SaveHighScore
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class GameViewModel @Inject constructor(
    private val getWordsUseCase: GetWords,
    private val wordManager: WordManager,
    private val saveHighScore: SaveHighScore,
    private val getHighScoreUseCase: GetHighScore
) : BaseViewModel() {

    private var currentScore = 0

    private val wordState = MutableLiveData<Word>()
    val wordStateView: LiveData<Word> = wordState

    private val scoreState = MutableLiveData<String>()
    val scoreViewState: LiveData<String> = scoreState

    private val highScoreState = MutableLiveData<String>()
    val highScoreViewState: LiveData<String> = highScoreState

    private val gameState = MutableLiveData<GameState>()
    val gameStateView: LiveData<GameState> = gameState

    private fun getNextWord() {
        if (wordManager.hasNext()) {
            wordState.value = wordManager.nextWord()
            saveHighScore(currentScore)
        } else {
            gameState.value = GameState.GameOver(currentScore.toString())
            saveHighScore(currentScore)
            currentScore = 0
            wordManager.reset()
            disposable.clear()
        }
    }

    fun restart() {
        getHighScore()
        getWords()
    }

    fun getHighScore() {
        highScoreState.value = getHighScoreUseCase().toString()
    }

    fun getWords() {
        disposable.add(getWordsUseCase()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { loadingState.value = true }
            .doOnComplete { loadingState.value = false }
            .subscribe({
                wordManager.words = it.toMutableList()
                scoreState.value = currentScore.toString()
                gameState.value = GameState.Started
                getNextWord()
            }, {
                errorState.value = Unit
            })
        )
    }

    fun onCorrectClicked() {
        if (wordManager.isCorrectWord()) {
            incrementScore()
        } else {
            decrementScore()
        }

        publishScore()
    }

    fun onWrongClicked() {
        if (!wordManager.isCorrectWord()) {
            incrementScore()
        } else {
            decrementScore()
        }

        publishScore()
    }

    fun onNoAnswer() {
        decrementScore()
        publishScore()
    }

    private fun publishScore() {
        scoreState.value = currentScore.toString()
        getNextWord()
    }

    private fun incrementScore() {
        currentScore += SCORE_VALUE
    }

    private fun decrementScore() {
        if (currentScore > 0) {
            currentScore -= SCORE_VALUE
        }
    }

    companion object {
        private const val SCORE_VALUE = 10
    }

    sealed class GameState {
        object Started : GameState()
        data class GameOver(val finalScore: String) : GameState()
    }
}