package com.example.wordmemorizer

import android.app.Application
import com.example.wordmemorizer.db.AppContainer
import com.example.wordmemorizer.db.IAppContainer

class MainApplication : Application() {

    lateinit var container: IAppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}