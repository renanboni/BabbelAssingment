package com.example.babbelassignment

import com.example.babbelassignment.di.components.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class BabbelApp: DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent
            .factory()
            .create(this)
    }
}