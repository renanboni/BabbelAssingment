package com.example.babbelassignment.core.ext

import android.content.res.AssetManager

fun AssetManager.readFile(fileName: String) = open(fileName)
    .bufferedReader()
    .use { it.readText() }