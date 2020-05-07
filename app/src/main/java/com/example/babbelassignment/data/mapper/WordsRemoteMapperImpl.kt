package com.example.babbelassignment.data.mapper

import com.example.babbelassignment.data.dto.WordDto
import com.example.babbelassignment.domain.entity.Word

class WordsRemoteMapperImpl : WordsRemoteMapper<WordDto, Word> {
    override fun mapFromDto(dto: WordDto): Word {
        return Word(english = dto.english.orEmpty(), spanish = dto.spanish.orEmpty())
    }
}