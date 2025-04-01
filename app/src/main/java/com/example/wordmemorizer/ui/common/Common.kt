package com.example.wordmemorizer.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ActionButton(text: String, onClick: () -> Unit, color: Color = Color(0xFF6200EE), enabled: Boolean = true) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(color),
        shape = RoundedCornerShape(8.dp),
        enabled = enabled
    ) {
        Text(text, color = Color.White)
    }
}

@Composable
fun RowCheckBox(text: String, checked: Boolean, onChecked: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onChecked
        )
        Text(
            text
        )
    }
}

