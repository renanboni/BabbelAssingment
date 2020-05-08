package com.example.babbelassignment.domain

import javax.inject.Inject
import kotlin.random.Random

class RandomAnswerImpl @Inject constructor() : RandomAnswer {

    override fun shouldGetWrongAnswer(): Boolean {
        return Random.nextBoolean()
    }

    override fun generateRandomIndex(from: Int, to: Int): Int {
        return Random.nextInt(from, to)
    }
}