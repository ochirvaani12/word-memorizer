package com.example.wordmemorizer

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.wordmemorizer.datastore.WordDatastore
import com.example.wordmemorizer.db.AppContainer
import com.example.wordmemorizer.db.IAppContainer

private const val LAYOUT_PREFERENCE_NAME = "word_preference"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = LAYOUT_PREFERENCE_NAME
)

class MainApplication : Application() {

    lateinit var container: IAppContainer
    lateinit var wordDatastore: WordDatastore

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
        wordDatastore = WordDatastore(dataStore)
    }
}