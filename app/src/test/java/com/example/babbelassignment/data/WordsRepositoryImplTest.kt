package com.example.babbelassignment.data

import com.example.babbelassignment.data.dto.WordDto
import com.example.babbelassignment.data.local.FileReader
import com.example.babbelassignment.data.mapper.WordsRemoteMapper
import com.example.babbelassignment.domain.entity.Word
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Test

class WordsRepositoryImplTest {

    private val fileReader: FileReader<List<WordDto>> = mock()
    private val mapper: WordsRemoteMapper<WordDto, Word> = mock()

    private val repository = WordsRepositoryImpl(fileReader, mapper)

    @Test
    fun `WHEN getWords is called THEN repository SHOULD complete`() {
        whenever(mapper.mapFromDto(any())).thenReturn(getWord())
        whenever(fileReader.readFromAssets()).thenReturn(Observable.just(listOf(getWordDto())))

        repository.getWords()
            .test()
            .assertComplete()
    }

    @Test
    fun `WHEN getWords is called THEN repository SHOULD call fileReader`() {
        whenever(mapper.mapFromDto(any())).thenReturn(getWord())
        whenever(fileReader.readFromAssets()).thenReturn(Observable.just(listOf(getWordDto())))

        repository.getWords()

        verify(fileReader).readFromAssets()
    }

    @Test
    fun `WHEN getWords is called THEN repository SHOULD return list of words`() {
        val wordsDto = listOf(getWordDto(), getWordDto())
        val expectedWords = mutableListOf<Word>()

        whenever(fileReader.readFromAssets()).thenReturn(Observable.just(wordsDto))

        wordsDto.forEach {
            val word = getWord()
            expectedWords.add(word)
            stubMapper(it, word)
        }

        repository.getWords()
            .test()
            .assertValue(expectedWords)
    }

    private fun stubMapper(from: WordDto, to: Word) {
        whenever(mapper.mapFromDto(from)).thenReturn(to)
    }

    private fun getWordDto(): WordDto {
        return WordDto(
            english = "hi",
            spanish = "hola"
        )
    }

    private fun getWord(): Word {
        return Word(
            english = "hi",
            spanish = "hola"
        )
    }
}