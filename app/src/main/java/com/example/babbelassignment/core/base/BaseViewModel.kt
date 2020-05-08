package com.example.babbelassignment.core.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {

    protected val disposable = CompositeDisposable()

    protected val loadingState = MutableLiveData<Boolean>()
    val loadingViewState: LiveData<Boolean> = loadingState

    protected val errorState = MutableLiveData<Unit>()
    val errorViewState: LiveData<Unit> = errorState

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}