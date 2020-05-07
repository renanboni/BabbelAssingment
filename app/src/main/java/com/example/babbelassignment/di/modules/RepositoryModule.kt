package com.example.babbelassignment.di.modules

import android.content.Context
import android.content.SharedPreferences
import com.example.babbelassignment.data.ScoreRepositoryImpl
import com.example.babbelassignment.data.WordsRepositoryImpl
import com.example.babbelassignment.data.dto.WordDto
import com.example.babbelassignment.data.local.FileReader
import com.example.babbelassignment.data.local.WordReader
import com.example.babbelassignment.data.mapper.WordsRemoteMapper
import com.example.babbelassignment.data.mapper.WordsRemoteMapperImpl
import com.example.babbelassignment.domain.entity.Word
import com.example.babbelassignment.domain.repository.ScoreRepository
import com.example.babbelassignment.domain.repository.WordsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("BabbelAssignment", Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideScoreRepository(sharedPreferences: SharedPreferences): ScoreRepository {
        return ScoreRepositoryImpl(sharedPreferences)
    }

    @Singleton
    @Provides
    fun provideWordsRepository(
        fileReader: FileReader<List<WordDto>>,
        mapper: WordsRemoteMapper<WordDto, Word>
    ): WordsRepository {
        return WordsRepositoryImpl(
            fileReader,
            mapper
        )
    }

    @Singleton
    @Provides
    fun provideWordReader(context: Context): WordReader {
        return WordReader(context)
    }

    @Singleton
    @Provides
    fun provideWordRemoteMapper(): WordsRemoteMapperImpl {
        return WordsRemoteMapperImpl()
    }
}