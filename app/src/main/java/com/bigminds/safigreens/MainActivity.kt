package com.bigminds.safigreens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bigminds.safigreens.ui.theme.SafiGreensTheme
import androidx.compose.ui.tooling.preview.Preview as Preview1

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
                    AppNavHost()
            PaymentScreen(navController = rememberNavController())
                }
            }
        }





@Preview1(showBackground = true, showSystemUi = true)
@Composable
fun AppNavHostPreview() {
    AppNavHost()
}

@Preview1(showBackground = true, showSystemUi = true)
@Composable
fun PaymentScreenPreview(navController: NavHostController) {
        PaymentScreen(navController = rememberNavController())
    }
