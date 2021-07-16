package com.friendsDomain.friendsapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.friendsDomain.friendsapp.ui.theme.FriendsAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FriendsAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting("Friends app")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "How are you ?  $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FriendsAppTheme {
        Greeting("Android")
    }
}