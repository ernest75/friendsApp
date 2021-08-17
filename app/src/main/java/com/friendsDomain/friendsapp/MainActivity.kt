package com.friendsDomain.friendsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.friendsDomain.friendsapp.ui.signup.SignUpScreen
import com.friendsDomain.friendsapp.ui.signup.SignUpViewModel
import com.friendsDomain.friendsapp.ui.theme.FriendsAppTheme
import com.friendsDomain.friendsapp.ui.timeline.TimelineScreen
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : ComponentActivity() {

    private val signUpViewModel: SignUpViewModel by viewModel()

    companion object {
        private const val SIGN_UP = "signUp"
        private const val TIME_LINE = "timeline"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            FriendsAppTheme {
                Surface(color = MaterialTheme.colors.background) {
                    NavHost(navController = navController, startDestination = Companion.SIGN_UP){
                        composable(Companion.SIGN_UP) {
                            SignUpScreen(signUpViewModel) { navController.navigate(TIME_LINE) }
                        }
                        composable(TIME_LINE){
                            TimelineScreen()
                        }
                    }
                }
            }
        }
    }
}

