package com.example.wordmemorizer.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.wordmemorizer.datastore.BOTH_WORD
import com.example.wordmemorizer.datastore.ENGLISH_WORD
import com.example.wordmemorizer.datastore.MONGOLIAN_WORD
import com.example.wordmemorizer.model.WordState
import com.example.wordmemorizer.ui.common.ActionButton

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailScreen(
    word: WordState? = null,
    wordPreference: String?,
    onDelete: () -> Unit,
    onInsert: () -> Unit,
    onUpdate: () -> Unit,
    onPrev: () -> Unit,
    onNext: () -> Unit
) {
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }
    var seeEnglishWord by remember { mutableStateOf((ENGLISH_WORD == wordPreference)) }
    var seeMongolianWord by remember { mutableStateOf((MONGOLIAN_WORD == wordPreference)) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Row (
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            ActionButton(
                "УСТГАХ",
                { deleteConfirmationRequired = true },
                color = MaterialTheme.colorScheme.error,
                enabled = word != null
            )
        }

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

        Column {
            if(word != null) {
                Column {
                    Text(
                        text = if (seeMongolianWord) "*****" else  word.engWord ?: "",
                        style = TextStyle(fontSize = 24.sp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .combinedClickable(
                                onClick = {seeMongolianWord = false},
                                onLongClick = onUpdate
                            )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = if (seeEnglishWord) "*****" else word.word ?: "",
                        style = TextStyle(fontSize = 24.sp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .combinedClickable(
                                onClick = {seeEnglishWord = false},
                                onLongClick = onUpdate
                            )
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }
            } else {
                Text("No word yet")
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ActionButton("НЭМЭХ", onInsert)
                ActionButton("ЗАСАХ", onUpdate, enabled = word != null)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ActionButton("ӨМНӨХ", onPrev, enabled = word != null)
                ActionButton("ДАРАА", onNext, enabled = word != null)
            }
        }
        Row {}
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