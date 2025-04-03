package com.example.wordmemorizer.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wordmemorizer.model.WordState
import com.example.wordmemorizer.ui.common.ActionButton

@Composable
fun EditScreen(
    word: WordState,
    onBack: () -> Unit,
    onInsert: (word: WordState) -> Unit,
) {
    var engWord by remember { mutableStateOf(word.engWord ?: "") }
    var mongolianWord by remember { mutableStateOf(word.word ?: "") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        OutlinedTextField(
            value = engWord,
            onValueChange = { engWord = it },
            textStyle = TextStyle(fontSize = 24.sp, textAlign = TextAlign.Center),
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    "Enter English word",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = mongolianWord,
            onValueChange = { mongolianWord = it },
            textStyle = TextStyle(fontSize = 24.sp, textAlign = TextAlign.Center),
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    "Enter Mongolian word",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        )

        Spacer(modifier = Modifier.height(30.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ActionButton("БОЛИХ", onBack)
            ActionButton("ХАДГАЛАХ",  {
                    onInsert(word.copy(engWord = engWord, word = mongolianWord))
                }
            )
        }

    }
}