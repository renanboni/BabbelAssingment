package com.example.babbelassignment.domain.repository

interface ScoreRepository {
    fun getHighScore(): Int
    fun setHighScore(score: Int)
}