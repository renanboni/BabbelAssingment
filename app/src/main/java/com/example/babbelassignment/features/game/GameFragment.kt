package com.example.babbelassignment.features.game

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.animation.addListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.babbelassignment.R
import com.example.babbelassignment.core.ext.setVisibility
import com.example.babbelassignment.domain.entity.Word
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_game.*
import javax.inject.Inject

class GameFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<GameViewModel> { viewModelFactory }

    private var objectAnimator: ObjectAnimator? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.fragment_game,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.wordStateView.observe(viewLifecycleOwner, Observer { renderWord(it) })
        viewModel.scoreViewState.observe(viewLifecycleOwner, Observer { renderScore(it) })
        viewModel.loadingViewState.observe(viewLifecycleOwner, Observer { renderLoading(it) })
        viewModel.errorViewState.observe(viewLifecycleOwner, Observer { renderError() })
        viewModel.gameStateView.observe(viewLifecycleOwner, Observer { renderGameState(it) })

        start.setOnClickListener { viewModel.getWords() }
        correct.setOnClickListener { viewModel.onCorrectClicked() }
        wrong.setOnClickListener { viewModel.onWrongClicked() }
    }

    private fun renderGameState(gameState: GameViewModel.GameState) {
        when (gameState) {
            is GameViewModel.GameState.Started -> {
                start.visibility = View.GONE
                group.visibility = View.VISIBLE
            }
        }
    }

    private fun renderError() {
        // ERROR
    }

    private fun renderLoading(isLoading: Boolean) {
        loading.setVisibility(isLoading)
    }

    private fun renderScore(currentScore: String) {
        score.text = getString(R.string.score, currentScore)
    }

    private fun renderWord(word: Word) {
        english.text = word.english
        spanish.text = word.spanish

        animate()
    }

    private fun animate() {
        objectAnimator?.removeAllListeners()

        objectAnimator = ObjectAnimator.ofFloat(
            spanish,
            "translationY",
            0f,
            root.height.toFloat()
        ).also {
            it.duration = 5000
            it.addListener(onEnd = {
                viewModel.onNoAnswer()
            })
            it.start()
        }
    }
}






