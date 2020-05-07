package com.example.babbelassignment.data

import android.content.SharedPreferences
import com.example.babbelassignment.domain.repository.ScoreRepository
import javax.inject.Inject

class ScoreRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : ScoreRepository {

    companion object {
        private const val KEY_HIGH_SCORE = "key_high_score"
    }

    override fun getHighScore(): Int {
        return sharedPreferences
            .getInt(KEY_HIGH_SCORE, 0)
    }

    override fun setHighScore(score: Int) {
        sharedPreferences
            .edit()
            .putInt(KEY_HIGH_SCORE, score)
            .apply()
    }
}