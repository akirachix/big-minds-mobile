package com.bigminds.safigreens

import Nunito
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bigminds.safigreens.ui.theme.SafiGreensTheme
import kotlinx.coroutines.launch

@Composable
fun PaymentScreen(navController: NavController) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedPayment by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var showMpesaDialog by remember { mutableStateOf(false) }
    var showPinDialog by remember { mutableStateOf(false) }
    var pin by remember { mutableStateOf("") }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier
                        .background(Color(0xFF003A2A))
                        .padding(16.dp)
                ) {

                    NavigationDrawerItem(
                        label = { Text("Profile", color = Color.White) },
                        selected = false,
                        onClick = { Log.d("PaymentScreen", "Profile clicked"); scope.launch { drawerState.close() } }
                    )
                    NavigationDrawerItem(
                        label = { Text("Settings", color = Color.White) },
                        selected = false,
                        onClick = { Log.d("PaymentScreen", "Settings clicked"); scope.launch { drawerState.close() } }
                    )

                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF003A2A))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier.fillMaxWidth().height(100.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu",
                    modifier = Modifier
                        .size(34.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            Log.d("PaymentScreen", "Menu clicked")
                            scope.launch { drawerState.open() }
                        },
                    tint = Color.White
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Choose payment method",
                    color = Color.White,
                    fontSize = 27.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontFamily = Nunito
                )
                Spacer(modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "Pay with",
                color = Color.White,
                fontSize = 27.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontFamily = Nunito
            )
            Spacer(modifier = Modifier.height(35.dp))



            Box(
                modifier = Modifier
                    .drawBehind {
                        drawIntoCanvas { canvas ->
                            val paint = android.graphics.Paint().apply {
                                color = Color.Green.copy(alpha = 0.3f).toArgb()
                                maskFilter = android.graphics.BlurMaskFilter(8f, android.graphics.BlurMaskFilter.Blur.NORMAL)
                            }
                            val left = -4.dp.toPx()
                            val top = -4.dp.toPx()
                            val right = size.width + 4.dp.toPx()
                            val bottom = size.height + 4.dp.toPx()
                            canvas.nativeCanvas.drawRoundRect(
                                left, top, right, bottom,
                                0.dp.toPx(), 0.dp.toPx(),
                                paint
                            )
                        }
                    }
                    .background(Color(0xFF003a2a), shape = RoundedCornerShape(0.dp))
                    .padding(16.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        Log.d("PaymentScreen", "M-PESA clicked")
                        selectedPayment = "M-PESA"
                        showMpesaDialog = true
                    }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.mpesa),
                        contentDescription = "M-PESA Logo",
                        modifier = Modifier
                            .size(70.dp)
                            .clip(CircleShape)
                            .background(Color.Green)
                            .padding(4.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "M-PESA",
                        color = Color.White,
                        fontSize = 27.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = Nunito
                    )
                }
            }


            Box(
                modifier = Modifier.padding(top = 80.dp)
                    .background(Color(0xFF003a2a), shape = RoundedCornerShape(0.dp))
                    .padding(10.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))

            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.attachmoney),
                        contentDescription = "Cash Icon",
                        modifier = Modifier
                            .size(70.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .padding(4.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Pay with Cash",
                        color = Color.White,
                        fontSize = 27.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = Nunito
                    )
                }
            }

            Spacer(modifier = Modifier.height(34.dp))
            Button(
                onClick = {
                    Log.d("PaymentScreen", "Continue button clicked")
                    showPinDialog = true
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(58.dp)
            ) {
                Text("Continue Shopping", color = Color(0xFF003A2A), fontSize = 20.sp, fontWeight = FontWeight.Bold, fontFamily = Nunito)
            }

            if (showMpesaDialog && selectedPayment == "M-PESA") {
                Log.d("PaymentScreen", "Dialog should be visible: showMpesaDialog=$showMpesaDialog, selectedPayment=$selectedPayment")
                Dialog(
                    onDismissRequest = {
                        Log.d("PaymentScreen", "Dialog dismissed")
                        showMpesaDialog = false
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .background(Color.White, RoundedCornerShape(16.dp))
                            .padding(16.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth(0.9f)
                        ) {
                            Text(
                                text = "Enter phone number",
                                color = Color.Black,
                                fontSize = 22.sp,
                                modifier = Modifier.padding(bottom = 8.dp),
                                fontFamily = Nunito,
                                fontWeight = FontWeight.Bold

                            )
                            OutlinedTextField(
                                value = phoneNumber,
                                onValueChange = { phoneNumber = it },
                                placeholder = { Text("Phone number", color = Color.Gray, fontSize = 18.sp, fontFamily = Nunito, fontWeight = FontWeight.Bold) },
                                leadingIcon = { Text("+254", color = Color.Black, fontFamily = Nunito, fontWeight = FontWeight.Bold) },
                                colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = Color.Black,
                                    unfocusedTextColor = Color.Black,
                                    focusedBorderColor = Color.Gray,
                                    unfocusedBorderColor = Color.Gray,
                                    cursorColor = Color.Black,
                                    focusedLeadingIconColor = Color.Black,
                                    unfocusedLeadingIconColor = Color.Black,
                                    focusedLabelColor = Color.Black,
                                    unfocusedLabelColor = Color.Black
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.Transparent),
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = {
                                    Log.d("PaymentScreen", "Continue clicked, phoneNumber=$phoneNumber")
                                    showMpesaDialog = false
                                    showPinDialog = true
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF006400)),
                                modifier = Modifier
                                    .fillMaxWidth(0.6f)
                                    .height(48.dp)
                            ) {
                                Text("Continue", color = Color.White, fontSize = 18.sp, fontFamily = Nunito, fontWeight = FontWeight.Bold)
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(
                                onClick = {
                                    Log.d("PaymentScreen", "Cancel clicked")
                                    showMpesaDialog = false
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                                modifier = Modifier
                                    .fillMaxWidth(0.6f)
                                    .height(48.dp)
                            ) {
                                Text("Cancel", color = Color.White, fontSize = 18.sp, fontFamily = Nunito, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }


            if (showPinDialog) {
                Log.d("PaymentScreen", "PIN dialog should be visible: showPinDialog=$showPinDialog")
                Dialog(
                    onDismissRequest = {
                        Log.d("PaymentScreen", "PIN dialog dismissed")
                        showPinDialog = false
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .background(Color.White, RoundedCornerShape(16.dp))
                            .padding(16.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth(0.9f)

                        )
                        {
                            Text(
                                text = "1000ksh will be deducted from ####### ...................\n" +
                                        ".............",
                                color = Color.Black,
                                fontSize = 20.sp,
                                modifier = Modifier.padding(bottom = 8.dp),
                                fontFamily = Nunito,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Enter Your Pin",
                                color = Color(0xFF003a2a),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 6.dp),
                                fontFamily = Nunito
                            )
                            OutlinedTextField(
                                value = pin,
                                onValueChange = { pin = it },
                                label = null,
                                placeholder = { Text("Enter PIN", color = Color.Gray, fontSize = 18.sp, fontFamily = Nunito, fontWeight = FontWeight.Bold) },
                                colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = Color.Black,
                                    unfocusedTextColor = Color.Black,
                                    focusedBorderColor = Color.Gray,
                                    unfocusedBorderColor = Color.Gray,
                                    cursorColor = Color.Black
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.Transparent),
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = {
                                    Log.d("PaymentScreen", "PIN submitted: $pin")
                                    if (pin.length == 4) {
                                        showPinDialog =false

                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF006400)),
                                modifier = Modifier
                                    .fillMaxWidth(0.6f)
                                    .height(48.dp)
                            ) {
                                Text("Confirm", color = Color.White, fontSize = 18.sp, fontFamily = Nunito, fontWeight = FontWeight.Bold)
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(
                                onClick = {
                                    Log.d("PaymentScreen", "PIN dialog cancelled")
                                    showPinDialog = false
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                                modifier = Modifier
                                    .fillMaxWidth(0.6f)
                                    .height(48.dp)
                            ) {
                                Text("Cancel", color = Color.White, fontSize = 18.sp, fontFamily = Nunito, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PaymentScreenPreview() {
    SafiGreensTheme {
        PaymentScreen(navController = rememberNavController())
    }
}
