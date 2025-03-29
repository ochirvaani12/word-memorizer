package com.example.wordmemorizer.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word")
data class Word (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val word: String,
    val engWord: String
)