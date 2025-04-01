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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wordmemorizer.ui.screen.DetailScreen
import com.example.wordmemorizer.ui.screen.EditScreen
import com.example.wordmemorizer.ui.screen.SettingsScreen
import com.example.wordmemorizer.ui.theme.WordMemorizerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WordMemorizerTheme {
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
    navController: NavHostController = rememberNavController(),
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    val viewModel: MainViewModel = viewModel()

    Scaffold(
        topBar = {
            MainAppBar(
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                modifier = modifier,
            )
        }
    ) { innerPadding ->
        val words by viewModel.words.collectAsState()
        val currentWord by viewModel.currentWord.collectAsState()

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
                    onDelete = {
                        viewModel.deleteWord(currentWord)
                    },
                    onInsert = {
                        navController.navigate(WordScreen.Edit.name)
                    },
                    onUpdate = {
                        navController.navigate(WordScreen.Edit.name)
                    },
                    onPrev = {
                        viewModel.setCurrentWord(if(words.indexOf(currentWord) != 0) words[words.indexOf(currentWord) - 1] else currentWord)
                    },
                    onNext = {
                        viewModel.setCurrentWord(if(words.indexOf(currentWord) != words.size - 1) words[words.indexOf(currentWord) + 1] else currentWord)
                    }
                )
            }
            composable(route = WordScreen.Edit.name) {
                EditScreen()
            }
            composable(route = WordScreen.Settings.name) {
                SettingsScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppBar(
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text("Картын апп") },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = "Settings"
                    )
                }
            }
        }
    )
}