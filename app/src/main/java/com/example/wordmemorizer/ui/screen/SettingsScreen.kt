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
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wordmemorizer.datastore.BOTH_WORD
import com.example.wordmemorizer.datastore.ENGLISH_WORD
import com.example.wordmemorizer.datastore.MONGOLIAN_WORD
import com.example.wordmemorizer.ui.common.ActionButton
import com.example.wordmemorizer.ui.common.RowCheckBox

@Composable
fun SettingsScreen(
    wordPreference: String?,
    onBack: () -> Unit,
    onSave: (checked: String) -> Unit
) {
    var checked by remember { mutableStateOf(wordPreference) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        RowCheckBox("Гадаад үгийг ил харуулна", checked == ENGLISH_WORD) { checked = ENGLISH_WORD };

        Spacer(modifier = Modifier.height(8.dp))

        RowCheckBox("Монгол үгийг ил харуулна", checked == MONGOLIAN_WORD) { checked = MONGOLIAN_WORD };

        Spacer(modifier = Modifier.height(8.dp))

        RowCheckBox("Хоёуланг нь ил харуулна", checked == BOTH_WORD) { checked = BOTH_WORD };

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ActionButton("БОЛИХ", onBack)
            ActionButton("ХАДГАЛАХ", { onSave(checked ?: BOTH_WORD) })
        }

    }
}