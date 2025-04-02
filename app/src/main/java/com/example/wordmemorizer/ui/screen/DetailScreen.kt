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
import com.example.wordmemorizer.model.WordState
import com.example.wordmemorizer.ui.common.ActionButton

@Composable
fun DetailScreen(
    word: WordState? = null,
    onDelete: () -> Unit,
    onInsert: () -> Unit,
    onUpdate: () -> Unit,
    onPrev: () -> Unit,
    onNext: () -> Unit
) {
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
            if(word != null) {
                ActionButton(
                    "УСТГАХ",
                    onDelete,
                    color = MaterialTheme.colorScheme.error,
                )
            }
        }

        Column {
            if(word != null) {
                Column {
                    OutlinedTextField(
                        value = word?.engWord ?: "",
                        readOnly = true,
                        onValueChange = { },
                        textStyle = TextStyle(fontSize = 24.sp),
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Enter English word") }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = word?.word ?: "",
                        readOnly = true,
                        onValueChange = { },
                        textStyle = TextStyle(fontSize = 24.sp),
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Enter Mongolian word") }
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
                if(word != null) {
                    ActionButton("ЗАСАХ", onUpdate)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ActionButton("ӨМНӨХ", onPrev)
                ActionButton("ДАРАА", onNext)
            }
        }
        Row {}
    }
}