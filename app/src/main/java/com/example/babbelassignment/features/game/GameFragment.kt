package com.example.babbelassignment.features.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.babbelassignment.R
import dagger.android.support.DaggerFragment

class GameFragment : DaggerFragment() {

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
}