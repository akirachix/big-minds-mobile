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
import androidx.compose.ui.tooling.preview.Preview
//import com.bigminds.safigreens.ui.theme.AuthenticationTheme
import androidx.compose.material3.Surface
import com.bigminds.safigreens.AuthNavHost
import com.bigminds.safigreens.ui.theme.SafiGreensTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AuthNavHost()
        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AuthNavHostPreview() {
    AuthNavHost()
}