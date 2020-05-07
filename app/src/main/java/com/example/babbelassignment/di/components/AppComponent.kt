package com.example.babbelassignment.di.components

import android.content.Context
import com.example.babbelassignment.BabbelApp
import com.example.babbelassignment.di.modules.AppModule
import com.example.babbelassignment.di.modules.HomeModule
import com.example.babbelassignment.di.modules.RepositoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        RepositoryModule::class,
        HomeModule::class
    ]
)
interface AppComponent : AndroidInjector<BabbelApp> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }
}