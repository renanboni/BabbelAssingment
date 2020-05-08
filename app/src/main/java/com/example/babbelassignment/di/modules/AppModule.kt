package com.example.babbelassignment.di.modules

import com.example.babbelassignment.core.DefaultSchedulerProvider
import com.example.babbelassignment.domain.SchedulerProvider
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class AppModule {
    @Singleton
    @Binds
    abstract fun provideScheduler(schedulerProvider: DefaultSchedulerProvider): SchedulerProvider
}