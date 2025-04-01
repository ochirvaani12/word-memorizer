package com.example.wordmemorizer.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.wordmemorizer.model.WordState

@Entity(tableName = "word")
data class Word (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val word: String,
    val engWord: String
)

fun Word.toState(): WordState {
    return WordState(id = this.id, word = this.word, engWord = this.engWord)
}