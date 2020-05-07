package com.example.babbelassignment.di.modules

import com.example.babbelassignment.features.home.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeModule {

    @ContributesAndroidInjector
    internal abstract fun homeFragment(): HomeFragment
}