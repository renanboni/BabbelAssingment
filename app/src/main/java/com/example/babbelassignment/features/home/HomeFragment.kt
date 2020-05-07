package com.example.babbelassignment.features.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.babbelassignment.R
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : DaggerFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.fragment_home,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        start.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_gameFragment)
        }
    }
}