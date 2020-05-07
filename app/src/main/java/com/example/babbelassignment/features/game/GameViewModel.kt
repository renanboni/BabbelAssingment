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

    init {
        getWords()
    }

    fun start() {
        if (wordManager.hasNext()) {
            wordManager.nextWord()
        } else {
            saveHighScore(currentScore)
        }
    }

    private fun getWords() {
        disposable.add(getWordsUseCase()
            .flatMap {
                wordManager.words = it.toMutableList()
                wordManager.observable
            }.subscribe {
                wordState.value = it
            })
    }

    fun onCorrectClicked() {
        if (wordManager.isCorrectWord()) {
            currentScore += 10
        } else {
            currentScore -= 10
        }

        scoreState.value = currentScore.toString()
        wordManager.nextWord()
    }

    fun onWrongClicked() {
        if (!wordManager.isCorrectWord()) {
            currentScore += 10
        } else {
            currentScore -= 10
        }

        scoreState.value = currentScore.toString()
        wordManager.nextWord()
    }
}