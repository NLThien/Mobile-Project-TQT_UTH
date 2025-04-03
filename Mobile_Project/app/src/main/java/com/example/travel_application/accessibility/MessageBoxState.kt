package com.example.travel_application.accessibility

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.travel_application.ui.screen.MessageBox

class MessageBoxState(
    val show: (title: String, message: String) -> Unit,
    val showWithAction: (
        title: String,
        message: String,
        onConfirm: () -> Unit,
        onDismiss: (() -> Unit)?
    ) -> Unit
)

@Composable
fun rememberMessageBoxState(): MessageBoxState {
    val (title, setTitle) = remember { mutableStateOf("") }
    val (message, setMessage) = remember { mutableStateOf("") }
    val (showDialog, setShowDialog) = remember { mutableStateOf(false) }
    val (onConfirm, setOnConfirm) = remember { mutableStateOf<(() -> Unit)?>(null) }
    val (onDismiss, setOnDismiss) = remember { mutableStateOf<(() -> Unit)?>(null) }

    if (showDialog) {
        MessageBox(
            title = title,
            message = message,
            onConfirm = {
                onConfirm?.invoke()
                setShowDialog(false)
            },
            onDismiss = {
                onDismiss?.invoke()
                setShowDialog(false)
            }
        )
    }

    return remember {
        MessageBoxState(
            show = { t, m ->
                setTitle(t)
                setMessage(m)
                setOnConfirm(null)
                setOnDismiss(null)
                setShowDialog(true)
            },
            showWithAction = { t, m, confirm, dismiss ->
                setTitle(t)
                setMessage(m)
                setOnConfirm { confirm() }
                setOnDismiss { dismiss?.invoke() }
                setShowDialog(true)
            }
        )
    }
}