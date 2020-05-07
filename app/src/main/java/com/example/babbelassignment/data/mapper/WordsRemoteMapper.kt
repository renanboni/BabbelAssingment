package com.example.babbelassignment.data.mapper

import com.example.babbelassignment.data.dto.WordDto
import com.example.babbelassignment.domain.entity.Word

interface WordsRemoteMapper<in D, out E> {
    fun mapFromDto(dto: WordDto): Word
}