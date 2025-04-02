package com.example.wordmemorizer

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.wordmemorizer.datastore.BOTH_WORD
import com.example.wordmemorizer.datastore.ENGLISH_WORD
import com.example.wordmemorizer.datastore.WordDatastore
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

class MainViewModel(
    private val wordRepository: WordRepository,
    private val wordDatastore: WordDatastore
): ViewModel() {

    private val _words = MutableStateFlow<List<WordState>>(emptyList())
    val words: StateFlow<List<WordState>> = _words.asStateFlow()

    private val _currentWord = MutableStateFlow<WordState?>(null)
    val currentWord: StateFlow<WordState?> = _currentWord.asStateFlow()

    private val _wordPreference = MutableStateFlow<String?>(null)
    val wordPreference: StateFlow<String?> = _wordPreference.asStateFlow()

    init {
        initDatastore()
        selectWord()
    }

    private fun initDatastore() {
        viewModelScope.launch {
            wordDatastore.getWordSettings.collect { word ->
                if (word != null) {
                    _wordPreference.value = word
                } else {
                    updateWordSettings(_wordPreference.value ?: BOTH_WORD)
                }
            }
        }
    }

    fun setCurrentWord(word: WordState?) {
        _currentWord.value = word;
    }

    fun insertWord(word: WordState) {
        viewModelScope.launch {
            wordRepository.insertWord(Word(word = word.word ?: "", engWord = word.engWord ?: ""))
            selectWord()
        }
    }

    fun updateWord(word: WordState) {
        viewModelScope.launch {
            wordRepository.updateWord(Word(id = word.id ?: 0, word = word.word ?: "", engWord = word.engWord ?: ""))
            selectWord()
        }
    }

    fun deleteWord(word: WordState) {
        viewModelScope.launch {
            wordRepository.deleteWord(Word(id = word.id ?: 0, word = word.word ?: "", engWord = word.engWord ?: ""))
            selectWord()
        }
    }

    fun selectWord() {
        viewModelScope.launch {
            wordRepository.selectWord().collect { words ->
                _words.value = words.map { it.toState() }
                _currentWord.value = if (_words.value.isNotEmpty()) _words.value[0] else null
            }
        }
    }

    fun updateWordSettings(wordSetting: String) {
        viewModelScope.launch {
            _wordPreference.value = wordSetting
            wordDatastore.saveWordSettings(wordSetting)
        }
    }

    companion object {
        val factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MainApplication)
                MainViewModel(application.container.wordRepository, application.wordDatastore)
            }
        }
    }
}