package com.example.babbelassignment.features.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.babbelassignment.domain.usecases.GetHighScore
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getHighScoreUseCase: GetHighScore
) : ViewModel() {

    private val highScoreState = MutableLiveData<String>()
    val highScoreViewState: LiveData<String> = highScoreState

    fun getHighScore() {
        highScoreState.value = getHighScoreUseCase().toString()
    }
}