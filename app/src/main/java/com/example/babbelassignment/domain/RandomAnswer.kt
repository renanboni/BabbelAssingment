package com.example.babbelassignment.domain

interface RandomAnswer {
    fun shouldGetWrongAnswer(): Boolean
    fun generateRandomIndex(from: Int, to: Int): Int
}