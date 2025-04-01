package com.example.wordmemorizer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.wordmemorizer.db.Word
import com.example.wordmemorizer.db.WordRepository
import com.example.wordmemorizer.db.toState
import com.example.wordmemorizer.model.WordState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val wordRepository: WordRepository): ViewModel() {

    private val _words = MutableStateFlow<List<WordState>>(emptyList())
    val words: StateFlow<List<WordState>> = _words.asStateFlow()

    private val _currentWord = MutableStateFlow(WordState())
    val currentWord: StateFlow<WordState> = _currentWord.asStateFlow()

    init {
        selectWord()
    }

    fun setCurrentWord(word: WordState) {
        _currentWord.value = word;
    }

    fun insertWord(word: WordState) {
        CoroutineScope(Dispatchers.IO).launch {
            wordRepository.insertWord(Word(word = word.word.toString(), engWord = word.engWord.toString()))
            selectWord()
        }
    }

    fun updateWord(word: WordState) {
        CoroutineScope(Dispatchers.IO).launch {
            wordRepository.updateWord(Word(id = word.id, word = word.word.toString(), engWord = word.engWord.toString()))
            selectWord()
        }
    }

    fun deleteWord(word: WordState) {
        CoroutineScope(Dispatchers.IO).launch {
            wordRepository.deleteWord(Word(id = word.id, word = word.word.toString(), engWord = word.engWord.toString()))
            selectWord()
        }
    }

    private fun selectWord() {
        CoroutineScope(Dispatchers.IO).launch {
            wordRepository.selectWord().collect { words ->
                _words.value = words.map { it.toState() }
            }

            wordRepository.selectWord().collect { words ->
                if(words.isNotEmpty()) _currentWord.value = words[0].toState() else _currentWord.value = WordState()
            }
        }
    }

    companion object {
        val factory : ViewModelProvider.Factory = viewModelFactory  {
            initializer {
                val application = (this[APPLICATION_KEY] as MainApplication)
                MainViewModel(application.container.wordRepository)
            }
        }
    }
}