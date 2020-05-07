package com.example.babbelassignment.data.local

interface FileReader <T> {
    fun readFromAssets(): T
}