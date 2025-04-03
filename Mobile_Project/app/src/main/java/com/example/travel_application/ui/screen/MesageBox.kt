package com.example.travel_application.ui.screen

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties
import com.example.travel_application.R

@Composable
fun MessageBox(
    title: String,
    message: String,
    onConfirm: () -> Unit,
    onDismiss: (() -> Unit)? = null,
    confirmText: String = "OK",
    dismissText: String = "CANCEL",
    modifier: Modifier = Modifier
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = { onDismiss?.invoke() },
        title = { Text(text = title) },
        text = { Text(text = message) },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = confirmText, color = Color(0xFF4CAF50))
            }
        },
        dismissButton = if (onDismiss != null) {
            {
                TextButton(onClick = { onDismiss.invoke() }) {
                    Text(text = dismissText, color = Color.Red)
                }
            }
        } else null,
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    )
}