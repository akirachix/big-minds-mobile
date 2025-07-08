package com.bigminds.safigreens

import Nunito
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay

sealed class Screens(val route: String) {
    object Splash : Screens("splash")
    object Welcome : Screens("welcome")
    object WelcomeMama : Screens("welcome_mama")
    object WelcomeCustomer : Screens("welcome_customer")
    object RoleSelection : Screens("role_selection")
    object AuthSelection : Screens("auth_selection")
    object SignUp : Screens("sign_up")
    object SignIn : Screens("sign_in")
    object ResetPin : Screens("reset_pin")

    object ConfirmPin : Screens("confirm_pin")
}


@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Screens.Splash.route) {
        composable(Screens.Splash.route) { SplashScreen(navController) }
        composable(Screens.Welcome.route) { WelcomeScreen(navController) }
        composable(Screens.WelcomeMama.route) { WelcomeScreenMama(navController) }
        composable(Screens.WelcomeCustomer.route) { WelcomeScreenCustomer(navController) }
        composable(Screens.RoleSelection.route) { RoleSelectionScreen(navController) }
        composable(Screens.AuthSelection.route) { AuthenticationSelectionScreen(navController) }
        composable(Screens.SignUp.route) { SignUpScreen(navController) }
        composable(Screens.SignIn.route) { SignInScreen(navController) }
        composable(Screens.ResetPin.route) { ResetPinScreen(navController) }
        composable ( Screens.ConfirmPin.route) {ConfirmPinScreen(navController) }
    }
}

@Composable
fun SplashScreen(navController: NavController) {
    LaunchedEffect(true) {
        delay(2000)
        navController.navigate(Screens.Welcome.route) {
            popUpTo(Screens.Splash.route) { inclusive = true }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 64.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.white),
                contentDescription = "Safi Greens Logo",
                modifier = Modifier.size(280.dp)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
                .align(Alignment.BottomCenter)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val width = size.width
                val height = size.height
                val path = Path().apply {
                    moveTo(0f, height * 0.3f)
                    cubicTo(
                        width * 0.2f, height * 0.1f,
                        width * 0.8f, height,
                        width, height * 0.6f
                    )
                    lineTo(width, height)
                    lineTo(0f, height)
                    close()
                }
                drawPath(path, color = Color(0xFF094C37))
            }
        }
    }
}

@Composable
fun WelcomeScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF094C37))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.52f)
                    .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
                    .background(Color.White)
            ) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(top = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Image(
                        painter = painterResource(id = R.drawable.greenbasket),
                        contentDescription = "Grocery Basket",
                        modifier = Modifier.size(500.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(36.dp))
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    Modifier.size(10.dp).clip(CircleShape).background(Color.White)
                )
                Spacer(Modifier.width(8.dp))
                Box(
                    Modifier.size(10.dp).clip(CircleShape).background(Color.White.copy(alpha = 0.7f))
                )
                Spacer(Modifier.width(8.dp))
                Box(
                    Modifier.size(10.dp).clip(CircleShape).background(Color.White.copy(alpha = 0.7f))
                )
            }
            Spacer(modifier = Modifier.height(36.dp))
            Text(
                text = "WELCOME",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Nunito,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "The easiest way to buy your\ngrocery.",
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 16.sp,
                fontFamily = Nunito,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(36.dp))
            Button(
                onClick = { navController.navigate(Screens.WelcomeMama.route) },
                modifier = Modifier
                    .padding(horizontal = 100.dp)
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(24.dp)
            )
            {
                Text(
                    "Continue",
                    fontSize = 18.sp,
                    fontFamily = Nunito,
                    color = Color(0xFF094C37),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun WelcomeScreenMama(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF094C37))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.52f)
                    .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
                    .background(Color.White)
            ) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(top = 60.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Image(
                        painter = painterResource(id = R.drawable.fruitsbasket),
                        contentDescription = "Grocery Basket",
                        modifier = Modifier.size(300.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(36.dp))
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    Modifier.size(10.dp).clip(CircleShape).background(Color.White.copy(alpha = 0.7f))
                )
                Spacer(Modifier.width(8.dp))
                Box(
                    Modifier.size(10.dp).clip(CircleShape).background(Color.White)
                )
                Spacer(Modifier.width(8.dp))
                Box(
                    Modifier.size(10.dp).clip(CircleShape).background(Color.White.copy(alpha = 0.7f))
                )
            }
            Spacer(modifier = Modifier.height(36.dp))
            Text(
                text = "SAFI GREENS",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Nunito,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = " Get fresh, clean vegetables from verified \nmama mbogas only.",
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 16.sp,
                fontFamily = Nunito,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(36.dp))
            Button(
                onClick = { navController.navigate(Screens.WelcomeCustomer.route) },
                modifier = Modifier
                    .padding(horizontal = 100.dp)
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text(
                    "Continue",
                    fontSize = 18.sp,
                    fontFamily = Nunito,
                    color = Color(0xFF094C37),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun WelcomeScreenCustomer(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF094C37))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.52f)
                    .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
                    .background(Color.White)
            ) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(top = 35.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Image(
                        painter = painterResource(id = R.drawable.cereals),
                        contentDescription = "Grocery Basket",
                        modifier = Modifier.size(350.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(36.dp))
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    Modifier.size(10.dp).clip(CircleShape).background(Color.White.copy(alpha = 0.7f))
                )
                Spacer(Modifier.width(8.dp))
                Box(
                    Modifier.size(10.dp).clip(CircleShape).background(Color.White.copy(alpha = 0.7f))
                )
                Spacer(Modifier.width(8.dp))
                Box(
                    Modifier.size(10.dp).clip(CircleShape).background(Color.White)
                )
            }
            Spacer(modifier = Modifier.height(36.dp))
            Text(
                text = "SAFI GREENS",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Nunito,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "   A wider reach to customers, In one\nonline door.",
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 16.sp,
                fontFamily = Nunito,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(36.dp))
            Button(
                onClick = { navController.navigate(Screens.RoleSelection.route) },
                modifier = Modifier
                    .padding(horizontal = 100.dp)
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text(
                    "Continue",
                    fontSize = 18.sp,
                    fontFamily = Nunito,
                    color = Color(0xFF094C37),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun RoleSelectionScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Image(
                painter = painterResource(id = R.drawable.white),
                contentDescription = "Safi Greens Logo",
                modifier = Modifier.size(330.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Spacer(modifier = Modifier.height(48.dp))
            Button(
                onClick = { navController.navigate(Screens.SignUp.route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF094C37)),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = "Customer",
                    fontSize = 18.sp,
                    fontFamily = Nunito,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            OutlinedButton(
                onClick = { navController.navigate(Screens.SignUp.route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
                    .height(48.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = "Mama mboga",
                    fontSize = 18.sp,
                    fontFamily = Nunito,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF094C37)
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .align(Alignment.BottomCenter)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val width = size.width
                val height = size.height
                val path = Path().apply {
                    moveTo(0f, height * 0.3f)
                    cubicTo(
                        width * 0.2f, height * 0.1f,
                        width * 0.8f, height,
                        width, height * 0.5f
                    )
                    lineTo(width, height)
                    lineTo(0f, height)
                    close()
                }
                drawPath(path, color = Color(0xFF094C37))
            }
        }
    }
}

@Composable
fun AuthenticationSelectionScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF094C37))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.green),
                    contentDescription = "Safi Greens Logo",
                    modifier = Modifier
                        .height(200.dp)
                        .width(280.dp)
                )
                Spacer(Modifier.width(12.dp))
            }

            Spacer(modifier = Modifier.height(64.dp))

            Button(
                onClick = { navController.navigate(Screens.SignUp.route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(10.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
            ) {
                Text(
                    text = "Sign Up",
                    fontSize = 18.sp,
                    color = Color(0xFF094C37),
                    fontFamily = Nunito,
                    fontWeight = FontWeight.SemiBold,

                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            OutlinedButton(
                onClick = { navController.navigate(Screens.SignIn.route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
                    .height(48.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.White
                ),
                border = BorderStroke(1.dp, Color.White),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = "Sign In",
                    fontSize = 18.sp,
                    fontFamily = Nunito,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                )
            }
            }
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
            .background(Color(0xFF094C37)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 18.dp)
        ) {
            Image(
                painterResource(id = R.drawable.green),
                contentDescription = null,
                modifier = Modifier.size(180.dp)
            )
        }
        Spacer(Modifier.height(24.dp))
        Text(
            "Sign Up",
            fontSize = 32.sp,
            color = Color.White,
            fontFamily = Nunito,
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
                    .padding(horizontal = 50.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(Modifier.height(20.dp))
                Text(
                    text = "Name", fontSize = 18.sp,
                    fontFamily = Nunito,
                    color = Color(0xFF094C37),
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 2.dp, bottom = 2.dp),
                    textAlign = TextAlign.Start
                )
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    placeholder = { Text("Enter name", fontFamily = Nunito,fontWeight = FontWeight.Normal,color = Color(0xFF094C37),) },
                    shape = RoundedCornerShape(10.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(14.dp))
                Text(
                    text = "Phone Number",
                    fontSize = 18.sp,
                    color = Color(0xFF094C37),
                    fontFamily = Nunito,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 2.dp, bottom = 2.dp),
                    textAlign = TextAlign.Start
                )
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    placeholder = { Text("+254", fontFamily = Nunito,fontWeight = FontWeight.Normal,color = Color(0xFF094C37),) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(14.dp))
                Text(
                    text = "Create pin", fontSize = 18.sp,
                    fontFamily = Nunito,
                    color = Color(0xFF094C37),
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 2.dp, bottom = 2.dp),
                    textAlign = TextAlign.Start
                )
                OutlinedTextField(
                    value = pin,
                    onValueChange = { pin = it },
                    placeholder = { Text("Create pin", fontFamily = Nunito,fontWeight = FontWeight.Normal,color = Color(0xFF094C37),) },
                    visualTransformation = if (showPin) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(10.dp))
                Text(
                    text = "Confirm pin", fontSize = 18.sp,
                    fontFamily = Nunito,
                    color = Color(0xFF094C37),
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 2.dp, bottom = 3.dp),
                    textAlign = TextAlign.Start
                )
                OutlinedTextField(
                    value = confirmPin,
                    onValueChange = { confirmPin = it },
                    placeholder = { Text("Confirm pin", fontFamily = Nunito, fontWeight = FontWeight.Normal,color = Color(0xFF094C37),) },
                    visualTransformation = if (showConfirm) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(20.dp))
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Button(
                        onClick = { /* Create account logic */ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF094C37)),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Create account", fontSize = 18.sp, fontFamily = Nunito,fontWeight = FontWeight.SemiBold)
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
                        fontFamily = Nunito,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF094C37),
                        textAlign = TextAlign.Start

                    )
                    TextButton(onClick = { navController.navigate(Screens.SignIn.route) }) {
                        Text(
                            text = "Sign In",
                            color = Color(0xFF094C37),
                            fontSize = 18.sp,
                            fontFamily = Nunito,
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
            .background(Color(0xFF094C37)),
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
                modifier = Modifier.size(180.dp)
            )
        }
        Spacer(Modifier.height(24.dp))
        Text(
            "Sign In",
            fontSize = 32.sp,
            fontFamily = Nunito,
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
                    .padding(horizontal = 50.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(Modifier.height(32.dp))
                Text(
                    text = "Phone Number", fontSize = 18.sp,
                    fontFamily = Nunito,
                    color = Color(0xFF094C37),
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp, bottom = 6.dp),
                    textAlign = TextAlign.Start
                )
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    placeholder = { Text("+254", fontFamily = Nunito,fontWeight = FontWeight.Normal,color = Color(0xFF094C37)) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    text = "Pin", fontSize = 18.sp,
                    fontFamily = Nunito,
                    color = Color(0xFF094C37),
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp, bottom = 6.dp),
                    textAlign = TextAlign.Start
                )
                OutlinedTextField(
                    value = pin,
                    onValueChange = { pin = it },
                    placeholder = { Text("Enter your pin", fontFamily = Nunito,fontWeight = FontWeight.Normal,color = Color(0xFF094C37)) },
                    visualTransformation = if (showPin) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.NumberPassword,
                        imeAction = ImeAction.Done
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                )
                TextButton(
                    onClick = { navController.navigate(Screens.ResetPin.route) },
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(top = 4.dp, bottom = 2.dp)
                ) {
                    Text(
                        "Forgot PIN?",
                        color = Color(0xFF094C37),
                        fontFamily = Nunito,
                        fontWeight = FontWeight.Bold,
                    )
                }
                Spacer(Modifier.height(16.dp))
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { /* Sign in logic */ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF094C37)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Sign In", fontSize = 18.sp,fontFamily = Nunito,fontWeight = FontWeight.SemiBold)
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
                        fontFamily = Nunito,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Start
                    )
                    TextButton(onClick = { navController.navigate(Screens.SignUp.route) }) {
                        Text(
                            text = "Sign Up",
                            color = Color(0xFF094C37),
                            fontSize = 18.sp,
                            fontFamily = Nunito,
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
                    .padding(horizontal = 50.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(Modifier.height(24.dp))
                Text(
                    "Reset Pin",
                    fontSize = 28.sp,
                    fontFamily = Nunito,
                    color = Color(0xFF094C37),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "Enter the code sent to your phone number",
                    fontSize = 14.sp,
                    fontFamily = Nunito,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF094C37),
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
                                    code = code.substring(0, idx) + it + code.substring(
                                        idx + 1,
                                        code.length.coerceAtMost(4)
                                    )
                                }
                            },
                            modifier = Modifier
                                .width(45.dp)
                                .height(55.dp),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                            maxLines = 1
                        )
                    }
                }
                Text(
                    text = "Create Pin", fontSize = 18.sp,
                    fontFamily = Nunito,
                    color = Color(0xFF094C37),
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp, bottom = 2.dp),
                    textAlign = TextAlign.Start
                )
                OutlinedTextField(
                    value = pin,
                    onValueChange = { pin = it },
                    placeholder = { Text("Enter your pin", fontFamily = Nunito,fontWeight = FontWeight.Normal,color = Color(0xFF094C37)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                )
                Text(
                    text = "Confirm Pin", fontSize = 18.sp,
                    fontFamily = Nunito,
                    color = Color(0xFF094C37),
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp, bottom = 6.dp),
                    textAlign = TextAlign.Start
                )
                OutlinedTextField(
                    value = confirmPin,
                    onValueChange = { confirmPin = it },
                    placeholder = { Text("confirm pin", fontFamily = Nunito,fontWeight = FontWeight.Normal,color = Color(0xFF094C37)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp)
                )
                Button(
                    onClick = { navController.navigate(Screens.ConfirmPin.route) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(top = 8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF094C37)),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Continue",fontFamily = Nunito,fontWeight = FontWeight.SemiBold)
                }
                OutlinedButton(
                    onClick = { navController.navigate(Screens.SignIn.route) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .padding(top = 8.dp),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Cancel", fontFamily = Nunito,fontWeight = FontWeight.SemiBold,color = Color(0xFF094C37))
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
            Canvas(modifier = Modifier.fillMaxSize()) {
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
                    color = Color(0xFF094C37)
                )
            }
        }
    }
}

@Composable

fun ConfirmPinScreen(navController: NavController){

    Column (modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .background(Color(0xFF094C37)),
        verticalArrangement =Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
        )
    {
        Image(
            painterResource(id = R.drawable.confirm),
            contentDescription = null,
            modifier = Modifier.size(150.dp)
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "Your pin has been\nreset!",
            textAlign = TextAlign.Center,
            fontSize = 26.sp,
            fontFamily = Nunito,
            color = Color(0xFFFFFFFF),
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(25.dp))

        Button(
            onClick = { navController.navigate(Screens.SignIn.route) },
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .height(50.dp),
//                .padding(horizontal = 45.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFFFFF)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Done", fontFamily = Nunito, fontSize = 21.sp, fontWeight = FontWeight.Bold,color = Color(0xFF094C37))

        }
    }

}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen(navController = rememberNavController())
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen(navController = rememberNavController())
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WelcomeScreenMamaPreview() {
    WelcomeScreenMama(navController = rememberNavController())
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WelcomeScreenCustomerPreview() {
    WelcomeScreenCustomer(navController = rememberNavController())
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RoleSelectionScreenPreview() {
    RoleSelectionScreen(navController = rememberNavController())
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AuthenticationSelectionScreenPreview() {
    AuthenticationSelectionScreen(navController = rememberNavController())
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ConfirmPinPreview(){
    ConfirmPinScreen(navController = rememberNavController())
}

