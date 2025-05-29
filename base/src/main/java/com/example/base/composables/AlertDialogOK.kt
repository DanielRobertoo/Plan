package com.example.chat.ui.base

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.domain.model.post


@Preview(showBackground = true)
@Composable
fun AlertDialogOKPreview() {
    AlertDialogOK(message = "Mensaje del cuadro dialogo", onDismiss = {})
}

@Composable
fun AlertDialogOK(
    title: String = "",
    message: String,
    onDismiss: () -> Unit
) {

    val dialogState = remember { mutableStateOf(true) }

    if (dialogState.value) {
        AlertDialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            ),
            title = {
                if (title.isNotEmpty()) {
                    Text(
                        text = title,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            },
            text = {
                Text(
                    text = message,
                    fontSize = 18.sp,
                    color = Color.Gray,
                    lineHeight = 22.sp
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        dialogState.value = false
                        onDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.White
                    ),
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(
                        text = "OK",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            },
            shape = RoundedCornerShape(16.dp),
            containerColor = Color.White,
        )
    }
}

@Composable
fun AlertDialogYesNoPost2(
    title: String = "",
    message: String,
    onDismiss: () -> Unit,
    onAccept: (post: post) -> Unit,
    post: post
) {
    // Estado del diálogo
    val dialogState = remember { mutableStateOf(true) }

    if (dialogState.value) {
        AlertDialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            ),
            title = {
                if (title.isNotEmpty()) {
                    Text(
                        text = title,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                    Text(post.description)

                    Column {
                        Text(post.gym)
                        Text("${post.date} / ${post.moment_day}")
                    }
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "")
                        Text(post.post_creator_username)
                    }

                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        dialogState.value = false
                        onAccept(post)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.White
                    ),
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(
                        text = "Yes",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        dialogState.value = false
                        onDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.White
                    ),
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(
                        text = "No",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            },
            shape = RoundedCornerShape(16.dp), // Bordes redondeados del diálogo
            containerColor = Color.White, // Color de fondo del diálogo
        )
    }
}

@Composable
fun AlertDialogYesNoPost(
    title: String = "",
    message: String = "",
    post: post,
    onDismiss: () -> Unit,
    onAccept: (post) -> Unit,
) {
    val openDialog = remember { mutableStateOf(true) }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
                onDismiss()
            },
            title = {
                if (title.isNotEmpty()) {
                    Text(
                        text = title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (message.isNotEmpty()) {
                        Text(
                            text = message,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    Text(
                        text = post.description,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.AccountCircle,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = post.post_creator_username,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "${post.gym} - ${post.date} (${post.moment_day})",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        openDialog.value = false
                        onAccept(post)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.White
                    )
                ) {
                    Text("Aceptar", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = {
                        openDialog.value = false
                        onDismiss()
                    }
                ) {
                    Text("Cancelar")
                }
            },
            shape = RoundedCornerShape(16.dp),
            containerColor = MaterialTheme.colorScheme.surface,
        )
    }
}

@Composable
fun AlertDialogYesNo(
    title: String = "",
    message: String,
    onDismiss: () -> Unit,
    onAccept: () -> Unit,
) {
    // Estado del diálogo
    val dialogState = remember { mutableStateOf(true) }

    if (dialogState.value) {
        AlertDialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            ),
            title = {
                if (title.isNotEmpty()) {
                    Text(
                        text = title,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            },
            text = {
                Text(
                    text = message,
                    fontSize = 18.sp,
                    color = Color.Gray,
                    lineHeight = 22.sp
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        dialogState.value = false
                        onAccept()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.White
                    ),
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(
                        text = "Yes",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        dialogState.value = false
                        onDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.White
                    ),
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(
                        text = "No",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            },
            shape = RoundedCornerShape(16.dp), // Bordes redondeados del diálogo
            containerColor = Color.White, // Color de fondo del diálogo
        )
    }
}
