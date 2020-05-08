package com.example.babbelassignment.features.game

import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.animation.addListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.babbelassignment.R
import com.example.babbelassignment.core.ext.gone
import com.example.babbelassignment.core.ext.setVisibility
import com.example.babbelassignment.core.ext.show
import com.example.babbelassignment.domain.entity.Word
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_game.*
import javax.inject.Inject

class GameFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<GameViewModel> { viewModelFactory }

    private var objectAnimator: ObjectAnimator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getHighScore()
    }

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

        setupObservers()
        setupViews()
    }

    private fun setupObservers() {
        viewModel.wordStateView.observe(viewLifecycleOwner, Observer { renderWord(it) })
        viewModel.scoreViewState.observe(viewLifecycleOwner, Observer { renderScore(it) })
        viewModel.loadingViewState.observe(viewLifecycleOwner, Observer { renderLoading(it) })
        viewModel.errorViewState.observe(viewLifecycleOwner, Observer { renderError() })
        viewModel.gameStateView.observe(viewLifecycleOwner, Observer { renderGameState(it) })
        viewModel.highScoreViewState.observe(viewLifecycleOwner, Observer { renderHighScore(it) })
    }

    private fun renderHighScore(hs: String) {
        highscore.text = getString(R.string.highscore, hs)
    }

    private fun setupViews() {
        start.setOnClickListener { viewModel.getWords() }
        correct.setOnClickListener { viewModel.onCorrectClicked() }
        wrong.setOnClickListener { viewModel.onWrongClicked() }
    }

    private fun renderGameState(gameState: GameViewModel.GameState) {
        when (gameState) {
            is GameViewModel.GameState.Started -> {
                start.gone()
                clickToPlay.gone()
                group.show()
            }
            is GameViewModel.GameState.GameOver -> {
                group.gone()
                start.show()
                clickToPlay.show()
                objectAnimator?.removeAllListeners()
                displayGameOverDialog(gameState)
            }
        }
    }

    private fun displayGameOverDialog(gameState: GameViewModel.GameState.GameOver) {
        MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialogTheme)
            .setTitle(getString(R.string.congratulations))
            .setMessage(getString(R.string.final_score, gameState.finalScore))
            .setCancelable(false)
            .setPositiveButton(getString(R.string.play_again)) { _, _ -> viewModel.restart() }
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun renderError() {
        Toast.makeText(requireContext(), getString(R.string.error_message), Toast.LENGTH_LONG).show()
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






