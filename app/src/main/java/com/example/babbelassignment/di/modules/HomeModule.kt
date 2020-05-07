package com.example.babbelassignment.di.modules

import androidx.lifecycle.ViewModel
import com.example.babbelassignment.di.ViewModelFactoryModule
import com.example.babbelassignment.di.ViewModelKey
import com.example.babbelassignment.features.home.HomeFragment
import com.example.babbelassignment.features.home.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class HomeModule {

    @ContributesAndroidInjector(modules = [ViewModelFactoryModule::class])
    internal abstract fun homeFragment(): HomeFragment

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindViewModel(viewModel: HomeViewModel): ViewModel
}