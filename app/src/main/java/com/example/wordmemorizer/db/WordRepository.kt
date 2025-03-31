package com.example.wordmemorizer.db

import android.content.Context
import kotlinx.coroutines.flow.Flow


interface IWordRepository {
    fun selectWordStream(): Flow<List<Word>>

    fun getWordStream(id: Int): Flow<Word?>

    suspend fun insertWord(item: Word)

    suspend fun deleteWord(item: Word)

    suspend fun updateWord(item: Word)
}

class WordRepository(private val wordDao: WordDao) : IWordRepository {
    override fun selectWordStream(): Flow<List<Word>> = wordDao.selectWord()

    override fun getWordStream(id: Int): Flow<Word?> = wordDao.getWord(id)

    override suspend fun insertWord(item: Word) = wordDao.insert(item)

    override suspend fun deleteWord(item: Word) = wordDao.delete(item)

    override suspend fun updateWord(item: Word) = wordDao.update(item)
}

interface IAppContainer {
    val wordRepository: WordRepository
}

class AppContainer(private val context: Context) : IAppContainer {
    override val wordRepository: WordRepository by lazy {
        WordRepository(WordDatabase.getDatabase(context).wordDao())
    }
}