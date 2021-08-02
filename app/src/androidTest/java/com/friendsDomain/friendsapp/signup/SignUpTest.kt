package com.friendsDomain.friendsapp.signup

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.friendsDomain.friendsapp.MainActivity
import org.junit.Rule
import org.junit.Test

class SignUpTest {

    @get:Rule
    val signUpTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun performSignUp(){
        launchSignUpScreen(signUpTestRule){
            typeEmail("ernest@friends.app")
            typePassword("password")
            submit()
        } verify {
            timelineScreenIsPresent()
        }
    }

}