package com.example.wordmemorizer

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wordmemorizer.model.WordState
import com.example.wordmemorizer.ui.screen.DetailScreen
import com.example.wordmemorizer.ui.screen.EditScreen
import com.example.wordmemorizer.ui.screen.SettingsScreen
import com.example.wordmemorizer.ui.theme.WordMemorizerTheme
import com.example.wordmemorizer.worker.scheduleNotification

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WordMemorizerTheme {
                val windowSize = calculateWindowSizeClass(this)

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainApp()
                }
            }
        }
    }
}

enum class WordScreen(s: String) {
    Detail("Detail"),
    Edit("Edit"),
    Settings("Settings")
}

@Composable
fun MainApp(
    viewModel: MainViewModel = viewModel(factory = MainViewModel.factory),
) {
    val navController: NavHostController = rememberNavController()

    Scaffold(
        topBar = {
            MainAppBar(
                onSetiings = {
                    navController.navigate(WordScreen.Settings.name)
                }
            )
        }
    ) { innerPadding ->
        val words by viewModel.words.collectAsState()
        val currentWord by viewModel.currentWord.collectAsState()
        val wordPreference by viewModel.wordPreference.collectAsState()

        NavHost(
            navController = navController,
            startDestination = WordScreen.Detail.name,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(route = WordScreen.Detail.name) {
                DetailScreen(
                    word = currentWord,
                    wordPreference = wordPreference,
                    onDelete = {
                        currentWord?.let { it1 -> viewModel.deleteWord(it1) }
                    },
                    onInsert = {
                        viewModel.setCurrentWord(null);
                        navController.navigate(WordScreen.Edit.name)
                    },
                    onUpdate = {
                        navController.navigate(WordScreen.Edit.name)
                    },
                    onPrev = {
                        (if( currentWord != null && words.indexOf(currentWord) != 0) words[words.indexOf(currentWord) - 1] else currentWord)?.let { it1 ->
                            viewModel.setCurrentWord(
                                it1
                            )
                        }
                    },
                    onNext = {
                        (if(currentWord != null && words.indexOf(currentWord) != words.size - 1) words[words.indexOf(currentWord) + 1] else currentWord)?.let { it1 ->
                            viewModel.setCurrentWord(
                                it1
                            )
                        }
                    }
                )
            }
            composable(route = WordScreen.Edit.name) {
                EditScreen(
                    word = currentWord ?: WordState(),
                    onBack = {
                        navController.navigate(WordScreen.Detail.name)
                        viewModel.selectWord()
                    },
                    onInsert = { word ->
                        if(word.id != null) {
                            viewModel.updateWord(word);
                        } else {
                            viewModel.insertWord(word);
                        }
                        viewModel.selectWord()
                        navController.navigate(WordScreen.Detail.name)
                    }
                )
            }
            composable(route = WordScreen.Settings.name) {
                SettingsScreen(
                    wordPreference = wordPreference,
                    onBack = {
                        navController.navigate(WordScreen.Detail.name)
                    },
                    onSave = {wordPreference ->
                        viewModel.updateWordSettings(wordPreference)
                        navController.navigate(WordScreen.Detail.name)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppBar(
    modifier: Modifier = Modifier,
    onSetiings: () -> Unit
) {
    TopAppBar(
        title = { Text("Картын апп") },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        actions = {
            IconButton(onClick = onSetiings) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More options"
                )
            }
        },
        modifier = modifier,
    )
}