package com.example.wordmemorizer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.wordmemorizer.db.Word
import com.example.wordmemorizer.db.WordRepository
import kotlinx.coroutines.flow.Flow

class MainViewModel(private val wordRepository: WordRepository): ViewModel() {

    fun selectWord(): Flow<List<Word>> = wordRepository.selectWord()

    fun getWord(id: Int): Flow<Word?> = wordRepository.getWord(id)

    companion object {
        val factory : ViewModelProvider.Factory = viewModelFactory  {
            initializer {
                val application = (this[APPLICATION_KEY] as MainApplication)
                MainViewModel(application.container.wordRepository)
            }
        }
    }
}