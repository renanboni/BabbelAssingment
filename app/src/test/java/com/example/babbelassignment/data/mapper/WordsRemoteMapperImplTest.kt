package com.example.babbelassignment.data.mapper

import com.example.babbelassignment.data.dto.WordDto
import org.junit.Assert.assertEquals
import org.junit.Test

class WordsRemoteMapperImplTest {

    private val mapper = WordsRemoteMapperImpl()

    @Test
    fun `GIVEN a WordDto WHEN mapFromDto is called THEN it should map it to Word`() {
        val wordDto = WordDto(english = "hi", spanish = "hola")

        val word = mapper.mapFromDto(wordDto)

        assertEquals(word.spanish, wordDto.spanish)
        assertEquals(word.english, wordDto.english)
    }
}