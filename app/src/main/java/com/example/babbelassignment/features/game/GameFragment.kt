package com.example.babbelassignment.features.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.babbelassignment.R
import com.example.babbelassignment.domain.entity.Word
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_game.*
import javax.inject.Inject

class GameFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<GameViewModel> { viewModelFactory }

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

        correct.setOnClickListener { viewModel.onCorrectClicked() }
        wrong.setOnClickListener { viewModel.onWrongClicked() }
    }

    private fun renderScore(currentScore: String) {
        score.text = getString(R.string.score, currentScore)
    }

    private fun renderWord(word: Word) {
        english.text = word.english
        spanish.text = word.spanish
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.start()
    }
}