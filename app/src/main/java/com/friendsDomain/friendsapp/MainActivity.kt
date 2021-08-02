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
import com.friendsDomain.friendsapp.ui.signup.SignUp
import com.friendsDomain.friendsapp.ui.theme.FriendsAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FriendsAppTheme {
                Surface(color = MaterialTheme.colors.background) {
                    SignUp()
                }
            }
        }
    }
}

