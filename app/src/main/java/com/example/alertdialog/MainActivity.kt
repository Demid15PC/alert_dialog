package com.example.alertdialog

import android.os.Bundle
import android.os.VibrationEffect
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.alertdialog.ui.theme.AlertDialogTheme

class MainActivity : ComponentActivity() {
       @OptIn(ExperimentalMaterialApi::class)
       override fun onCreate(savedInstanceState: Bundle?) {
              super.onCreate(savedInstanceState)
              setContent {
                     AlertDialogTheme {
                            val typing = remember { mutableStateOf("") }
                            val dialogState = remember { mutableStateOf(false) }
                            val visible = remember { mutableStateOf(false) }

                            //////////////////////////////////////////////
                            if (dialogState.value) GetAlert(dialogState, typing, visible)

                            Column(
                                   modifier = Modifier.fillMaxSize(),
                                   verticalArrangement = Arrangement.Bottom
                            ) {
                                   BottomSheetScaffold(
                                          modifier = Modifier.height(300.dp),
                                          sheetContent = {
                                                 Box(
                                                        modifier = Modifier.fillMaxSize(),
                                                        contentAlignment = Alignment.Center
                                                 ) {
                                                        Button(
                                                               onClick = {
                                                                      dialogState.value = true
                                                               },
                                                               modifier = Modifier
                                                                      .padding(horizontal = 16.dp)
                                                                      .fillMaxWidth()
                                                                      .height(64.dp)
                                                        ) {
                                                               Text(
                                                                      text = "Какая то кнопка",
                                                                      fontSize = MaterialTheme.typography.h4.fontSize
                                                               )
                                                        }
                                                 }
                                          }, sheetBackgroundColor = Color.Green
                                   ) {

                                   }
                            }
                     }
              }
       }
}

@Composable
fun GetAlert(
       dialogState: MutableState<Boolean>,
       typing: MutableState<String>,
       visible: MutableState<Boolean>
) {
       val context = LocalContext.current
       AlertDialog(
              shape = RoundedCornerShape(20.dp),
              onDismissRequest = {
                     dialogState.value = false
              },
              confirmButton = {
                     TextButton(onClick = {
                            dialogState.value = false
                            Toast.makeText(
                                   context,
                                   "Вы написали - " + typing.value,
                                   Toast.LENGTH_SHORT
                            ).show()
                     }) {
                            Text(text = "Ok")
                     }
              },
              dismissButton = {
                     TextButton(onClick = {
                            dialogState.value = false
                     }) {
                            Text(text = "Cancel")
                     }
              },
              title = {
                     Column(modifier = Modifier.fillMaxWidth()) {
                            Text(text = "Введите e-mail")
                            OutlinedTextField(
                                   value = typing.value, onValueChange = {
                                          typing.value = it
                                   },
                                   singleLine = true,
                                   label = {
                                          Text(text = "e-mail")
                                   },
                                   trailingIcon = {
                                          IconButton(onClick = {
                                                 visible.value = !visible.value
                                          }) {
                                                 if (!visible.value)
                                                        Icon(
                                                               painter = painterResource(id = R.drawable.ic_baseline_visibility_24),
                                                               contentDescription = "Visible"
                                                        )
                                                 else
                                                        Icon(
                                                               painter = painterResource(id = R.drawable.ic_baseline_visibility_off_24),
                                                               contentDescription = "unVisible"
                                                        )
                                          }
                                   },
                                   keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                                   keyboardActions = KeyboardActions { KeyboardActions.Default.onDone },
                                   textStyle = TextStyle(textDecoration = TextDecoration.None),
                                   visualTransformation =
                                   if (!visible.value) VisualTransformation.None
                                   else
                                          PasswordVisualTransformation()
                            )
                     }
              }
       )
}