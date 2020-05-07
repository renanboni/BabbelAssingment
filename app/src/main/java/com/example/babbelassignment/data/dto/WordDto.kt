package com.example.babbelassignment.data.dto

import com.google.gson.annotations.SerializedName

class WordDto(
    @SerializedName("text_eng") val english: String? = "",
    @SerializedName("text_spa") val spanish: String? = ""
)