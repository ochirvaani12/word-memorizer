package com.example.wordmemorizer.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Word)

    @Update
    suspend fun update(item: Word)

    @Delete
    suspend fun delete(item: Word)

    @Query("SELECT * from word WHERE id = :id")
    fun getWord(id: Int): Flow<Word>

    @Query("SELECT * from word ORDER BY word ASC")
    fun selectWord(): Flow<List<Word>>
}