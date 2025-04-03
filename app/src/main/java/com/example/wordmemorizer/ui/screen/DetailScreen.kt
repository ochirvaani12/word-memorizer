package com.example.wordmemorizer.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wordmemorizer.datastore.ENGLISH_WORD
import com.example.wordmemorizer.datastore.MONGOLIAN_WORD
import com.example.wordmemorizer.model.WordState
import com.example.wordmemorizer.ui.common.ActionButton

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailScreen(
    word: WordState? = null,
    wordPreference: String? = null,
    onDelete: () -> Unit,
    onInsert: () -> Unit,
    onUpdate: () -> Unit,
    onPrev: () -> Unit,
    onNext: () -> Unit
) {

    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }

    var seeEnglishWord by remember { mutableStateOf(ENGLISH_WORD == wordPreference) }
    var seeMongolianWord by remember { mutableStateOf(MONGOLIAN_WORD == wordPreference) }

    LaunchedEffect(wordPreference) {
        seeEnglishWord = (wordPreference == ENGLISH_WORD)
        seeMongolianWord = (wordPreference == MONGOLIAN_WORD)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        if (deleteConfirmationRequired) {
            DeleteConfirmationDialog(
                onDeleteConfirm = {
                    deleteConfirmationRequired = false
                    onDelete()
                },
                onDeleteCancel = { deleteConfirmationRequired = false },
                modifier = Modifier.padding(40.dp)
            )
        }

        if (word != null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(10.dp)
                    .border(BorderStroke(1.5.dp, Color(0xFF6200EE)), RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center // Centers content inside Box
            ) {
                Text(
                    text = if (seeMongolianWord) "*****" else word.engWord ?: "",
                    textAlign = TextAlign.Center,
                    style = TextStyle(fontSize = 35.sp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .combinedClickable(
                            onClick = { seeMongolianWord = false },
                            onLongClick = onUpdate
                        )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(10.dp)
                    .border(BorderStroke(1.5.dp, Color(0xFF6200EE)), RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center // Centers content inside Box
            ) {
                Text(
                    text = if (seeEnglishWord) "*****" else word.word ?: "",
                    textAlign = TextAlign.Center,
                    style = TextStyle(fontSize = 35.sp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .combinedClickable(
                            onClick = { seeEnglishWord = false },
                            onLongClick = onUpdate
                        )
                )
            }
        } else {
            Text("No word yet")
        }

        Spacer(modifier = Modifier.height(30.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ActionButton("НЭМЭХ", onInsert)
            ActionButton("ЗАСАХ", onUpdate, enabled = word != null)
            ActionButton(
                "УСТГАХ",
                { deleteConfirmationRequired = true },
                color = MaterialTheme.colorScheme.error,
                enabled = word != null
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ActionButton("ӨМНӨХ", onPrev, enabled = word != null)
            ActionButton("ДАРАА", onNext, enabled = word != null)
        }
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit, onDeleteCancel: () -> Unit, modifier: Modifier = Modifier
) {
    AlertDialog(onDismissRequest = { /* Do nothing */ },
        title = { Text("Анхаарах") },
        text = { Text("Та устгахдаа итгэлтэй байна уу") },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = "Үгүй")
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = "Тийм")
            }
        })
}