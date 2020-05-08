package com.example.babbelassignment.data

import android.content.SharedPreferences
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Test

class ScoreRepositoryImplTest {

    private val sharedPreferences: SharedPreferences = mock()
    private val editor: SharedPreferences.Editor = mock()

    private val repository = ScoreRepositoryImpl(sharedPreferences)

    @Test
    fun `WHEN getHighScore is called THEN repository SHOULD call getInt on shared preferences`() {
        repository.getHighScore()

        verify(sharedPreferences).getInt("key_high_score", 0)
    }

    @Test
    fun `WHEN setHighScore is called THEN repository SHOULD call putInt on shared preferences`() {
        whenever(sharedPreferences.edit()).thenReturn(editor)
        whenever(editor.putInt(any(), any())).thenReturn(editor)

        repository.setHighScore(10)

        verify(editor).putInt("key_high_score", 10)
    }
}