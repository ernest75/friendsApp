package com.friendsDomain.friendsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.friendsDomain.friendsapp.ui.signup.SignUp
import com.friendsDomain.friendsapp.ui.theme.FriendsAppTheme
import androidx.compose.ui.layout.ContentScale




class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            FriendsAppTheme {
                Surface(color = MaterialTheme.colors.background) {
                    NavHost(navController = navController, startDestination = "signUp"){
                        composable("signUp") {
                            SignUp(onSignedUp = {navController.navigate("timeline") })
                        }
                        composable("timeline"){
                            Image(
                                painterResource(id = R.drawable.ic_launcher_background),
                                contentDescription = "Timeline",
                                modifier = Modifier.fillMaxWidth(),
                                contentScale = ContentScale.Crop
                            )

                        }
                    }
                }
            }
        }
    }
}

