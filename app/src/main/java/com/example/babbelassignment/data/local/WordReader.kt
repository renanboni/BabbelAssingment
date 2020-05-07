package com.example.babbelassignment.data.local

import android.content.Context
import com.example.babbelassignment.core.ext.readFile
import com.example.babbelassignment.data.dto.WordDto
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

class WordReader(private val context: Context) : FileReader<List<WordDto>> {

    companion object {
        private const val FILE_NAME = "words_v2.json"
    }

    override fun readFromAssets(): List<WordDto> {
        val jsonString = context.assets.readFile(FILE_NAME)

        return try {
            GsonBuilder()
                .create()
                .fromJson(jsonString, object : TypeToken<List<WordDto>>() {}.type)
        } catch (e: Exception) {
            emptyList()
        }
    }
}