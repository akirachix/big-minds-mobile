package com.bigminds.safigreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AuthNavHost() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "sign_up") {
        composable("sign_up") { SignUpScreen(navController) }
        composable("sign_in") { SignInScreen(navController) }
        composable("reset_pin") { ResetPinScreen(navController) }
    }
}

@Composable
fun SignUpScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var pin by remember { mutableStateOf("") }
    var confirmPin by remember { mutableStateOf("") }
    var showPin by remember { mutableStateOf(false) }
    var showConfirm by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0B4D29)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Image(
                painterResource(id = R.drawable.green),
                contentDescription = null,
                modifier = Modifier.size(156.dp)
            )
        }
        Spacer(Modifier.height(24.dp))
        Text(
            "Sign Up",
            fontSize = 32.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        Spacer(Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                .background(Color.White)
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 28.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(Modifier.height(20.dp))
                Text(text = "Name",fontSize = 18.sp,  modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp, bottom = 2.dp),
                    textAlign = TextAlign.Start)
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    placeholder = {Text("Enter name")},
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(14.dp))
                Text(text = "Phone Number",fontSize = 18.sp,  modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp, bottom = 2.dp),
                    textAlign = TextAlign.Start)
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    placeholder = { Text("+254") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(14.dp))
                Text(text = "Create pin",fontSize = 18.sp,  modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp, bottom = 2.dp),
                    textAlign = TextAlign.Start)
                OutlinedTextField(
                    value = pin,
                    onValueChange = { pin = it },
                    placeholder = {Text("Create pin")},
                    visualTransformation = if (showPin) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(10.dp))
                Text(text = "Confirm pin",fontSize = 18.sp,  modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp, bottom = 3.dp),
                    textAlign = TextAlign.Start)
                OutlinedTextField(
                    value = confirmPin,
                    onValueChange = { confirmPin = it },
                    placeholder = {Text("Confirm pin")},
                    visualTransformation = if (showConfirm) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(20.dp))
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Button(
                        onClick = { /* Create account logic */ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF00331A)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Create account", fontSize = 18.sp)
                    }
                    IconButton(
                        onClick = { /* Fingerprint signup */ },
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .size(36.dp)
                    ) {
                    }
                }
                Spacer(Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Already have an account?",
                        textAlign = TextAlign.Start
                    )
                    TextButton(onClick = { navController.navigate("sign_in") }) {
                        Text(
                            text = "Sign In",
                            color = Color(0xFF0B4D29),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                }
            }
        }
    }
}

@Composable
fun SignInScreen(navController: NavController) {
    var phone by remember { mutableStateOf("") }
    var pin by remember { mutableStateOf("") }
    var showPin by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0B4D29)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(50.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Image(
                painterResource(id = R.drawable.green),
                contentDescription = null,
                modifier = Modifier.size(156.dp)
            )
        }
        Spacer(Modifier.height(24.dp))
        Text(
            "Sign In",
            fontSize = 32.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        Spacer(Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                .background(Color.White)
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(Modifier.height(32.dp))
                Text(text = "Phone Number",fontSize = 18.sp,  modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp, bottom = 6.dp),
                    textAlign = TextAlign.Start)
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    placeholder = { Text("+254") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                )
                Spacer(Modifier.height(16.dp))
                Text(text = "Pin",fontSize = 18.sp,  modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp, bottom = 6.dp),
                    textAlign = TextAlign.Start)
                OutlinedTextField(
                    value = pin,
                    onValueChange = { pin = it },
                    placeholder = { Text("Enter your pin") },
                    visualTransformation = if (showPin) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                TextButton(
                    onClick = { navController.navigate("reset_pin") },
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(top = 4.dp, bottom = 2.dp)
                ) {
                    Text(
                        "Forgot PIN?",
                        color = Color(0xFF0B4D29),
                        fontWeight = FontWeight.Bold,

                        )
                }
                Spacer(Modifier.height(16.dp))
                Row(Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically) {
                    Button(
                        onClick = { /* Create account logic */ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF00331A)
                        ),
                        shape = RoundedCornerShape(12.dp)

                    )
                    {
                        Text("Sign In", fontSize = 18.sp)
                    }
                    IconButton(
                        onClick = { /* Fingerprint login */ },
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .size(36.dp)
                    ) {
                    }
                }
                Spacer(Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Don’t have an account?",
                        textAlign = TextAlign.Start
                    )
                    TextButton(onClick = { navController.navigate("sign_up") }) {
                        Text(
                            text = "Sign Up",
                            color = Color(0xFF0B4D29),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun ResetPinScreen(navController: NavController) {
    var code by remember { mutableStateOf("") }
    var pin by remember { mutableStateOf("") }
    var confirmPin by remember { mutableStateOf("") }
    var showPin by remember { mutableStateOf(false) }
    var showConfirm by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFF)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(30.dp))
        Image(
            painterResource(id = R.drawable.white),
            contentDescription = null,
            modifier = Modifier.size(166.dp)
        )

        Spacer(Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                .background(Color.White)
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(Modifier.height(24.dp))
                Text(
                    "Reset Pin",
                    fontSize = 28.sp,
                    color = Color(0xFF0B4D29),
                    fontWeight = FontWeight.Bold

                )
                Text(
                    "Enter the code sent to your phone number",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 18.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    repeat(4) { idx ->
                        OutlinedTextField(
                            value = code.getOrNull(idx)?.toString() ?: "",
                            onValueChange = {
                                if (it.length <= 1) {
                                    code = code.substring(0, idx) + it + code.substring(idx + 1, code.length.coerceAtMost(4))
                                }
                            },
                            modifier = Modifier
                                .width(45.dp)
                                .height(55.dp),
                            singleLine = true,
                            maxLines = 1
                        )
                    }
                }
                Text(text = "Create Pin",fontSize = 18.sp,  modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp, bottom = 2.dp),
                    textAlign = TextAlign.Start)
                OutlinedTextField(
                    value = pin,
                    onValueChange = { pin = it },
                    placeholder = { Text("Enter your pin") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                )
                Text(text = "Confirm Pin",fontSize = 18.sp,  modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp, bottom = 6.dp),
                    textAlign = TextAlign.Start)
                OutlinedTextField(
                    value = confirmPin,
                    onValueChange = { confirmPin = it },
                    placeholder = { Text("confirm pin") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp)
                )
                Button(
                    onClick = { /* Create account logic */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(top = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00331A)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Continue")
                }
                OutlinedButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .padding(top = 8.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Cancel", color = Color(0xFF0B4D29))
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(80.dp)
                .align(Alignment.BottomCenter),
        ) {
            androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize()) {
                val width = size.width
                val height = size.height
                val path = Path().apply {
                    moveTo(0f, height * 0.1f)
                    cubicTo(
                        width * 0.15f, 0f,
                        width * 0.85f, height,
                        width, height * 0.3f
                    )
                    lineTo(width, height)
                    lineTo(0f, height)
                    close()
                }
                drawPath(
                    path = path,
                    color = Color(0xFF00331A)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SignUpScreenPreview() {
    SignUpScreen(navController = rememberNavController())
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SignInScreenPreview() {
    SignInScreen(navController = rememberNavController())
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ResetPinScreenPreview() {
    ResetPinScreen(navController = rememberNavController())
}


