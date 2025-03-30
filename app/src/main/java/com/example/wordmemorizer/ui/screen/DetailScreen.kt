package com.example.wordmemorizer.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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

@Composable
fun DetailScreen() {
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

@Preview(showBackground = true)
@Composable
fun PreviewDetailScreen() {
    DetailScreen()
}