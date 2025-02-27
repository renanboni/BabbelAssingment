package com.example.babbelassignment.data

import com.example.babbelassignment.data.dto.WordDto
import com.example.babbelassignment.data.local.FileReader
import com.example.babbelassignment.data.mapper.WordsRemoteMapper
import com.example.babbelassignment.domain.entity.Word
import com.example.babbelassignment.domain.repository.WordsRepository
import io.reactivex.Observable
import javax.inject.Inject

class WordsRepositoryImpl @Inject constructor(
    private val fileReader: FileReader<List<WordDto>>,
    private val mapper: WordsRemoteMapper<WordDto, Word>
) : WordsRepository {

    override fun getWords(): Observable<List<Word>> {
        return fileReader.readFromAssets().map { it.map { dto -> mapper.mapFromDto(dto) } }
    }
}