package com.example.babbelassignment.di.modules

import androidx.lifecycle.ViewModel
import com.example.babbelassignment.di.ViewModelFactoryModule
import com.example.babbelassignment.di.ViewModelKey
import com.example.babbelassignment.features.game.GameFragment
import com.example.babbelassignment.features.game.GameViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class GameModule {

    @ContributesAndroidInjector(modules = [ViewModelFactoryModule::class])
    internal abstract fun gameFragment(): GameFragment

    @Binds
    @IntoMap
    @ViewModelKey(GameViewModel::class)
    abstract fun bindViewModel(viewModel: GameViewModel): ViewModel
}