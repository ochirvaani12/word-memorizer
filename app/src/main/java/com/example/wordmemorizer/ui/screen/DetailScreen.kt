package com.example.wordmemorizer.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable

enum class WordScreen(s: String) {
    Detail("Detail"),
    Edit("Edit"),
    Settings("Settings")
}

@Composable
fun DetailScreen(
    navController: NavHostController = rememberNavController(),
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            DetailTopBar(
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                modifier = modifier,
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = WordScreen.Detail.name,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(route = WordScreen.Detail.name) {
                DetailComponent()
            }
            composable(route = WordScreen.Edit.name) {
                Text("Edit")
            }
            composable(route = WordScreen.Settings.name) {
                Text("Settings")
            }
        }
    }


}

@Composable
fun DetailComponent() {
    var englishWord by remember { mutableStateOf("three") }
    var mongolianWord by remember { mutableStateOf("гурав") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = englishWord,
            onValueChange = { englishWord = it },
            textStyle = TextStyle(fontSize = 24.sp),
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Enter English word") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = mongolianWord,
            onValueChange = { mongolianWord = it },
            textStyle = TextStyle(fontSize = 24.sp),
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Enter Mongolian word") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ActionButton("НЭМЭХ") { /* Add logic */ }
            ActionButton("ШИНЭЧЛЭХ") { /* Update logic */ }
            ActionButton("УСТГАХ") { /* Delete logic */ }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            NavigationButton("ДАРАА") { /* Next logic */ }
            NavigationButton("ӨМНӨХ") { /* Previous logic */ }
        }
    }
}

@Composable
fun ActionButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(Color(0xFF6200EE)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(text, color = Color.White)
    }
}

@Composable
fun NavigationButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(Color(0xFF6200EE)),
        modifier = Modifier.width(120.dp)
    ) {
        Text(text, color = Color.White)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTopBar(
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

@Preview(showBackground = true)
@Composable
fun PreviewDetailScreen() {
    DetailScreen()
}