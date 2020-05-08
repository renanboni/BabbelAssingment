package com.example.babbelassignment.data.local

import io.reactivex.Observable

interface FileReader <T> {
    fun readFromAssets(): Observable<T>
}