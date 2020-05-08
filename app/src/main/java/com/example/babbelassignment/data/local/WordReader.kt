package com.example.babbelassignment.data.local

import android.content.Context
import com.example.babbelassignment.core.ext.readFile
import com.example.babbelassignment.data.dto.WordDto
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import io.reactivex.Observable
import javax.inject.Inject

class WordReader @Inject constructor(
    private val context: Context
) : FileReader<List<WordDto>> {

    companion object {
        private const val FILE_NAME = "words_v2.json"
    }

    override fun readFromAssets(): Observable<List<WordDto>> {
        return try {
            val jsonString = context.assets.readFile(FILE_NAME)

            val words = GsonBuilder()
                .create()
                .fromJson<List<WordDto>>(jsonString, object : TypeToken<List<WordDto>>() {}.type)

            Observable.just(words)
        } catch (e: Throwable) {
            Observable.error(e)
        }
    }
}