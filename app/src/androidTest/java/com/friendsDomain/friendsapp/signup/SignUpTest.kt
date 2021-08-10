package com.friendsDomain.friendsapp.signup

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.friendsDomain.friendsapp.MainActivity
import com.friendsDomain.friendsapp.domain.user.InMemoryUserCatalog
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

class SignUpScreenTest {

    @get:Rule
    val signUpTestRule = createAndroidComposeRule<MainActivity>()

    private val userCatalog = InMemoryUserCatalog()

    private val signUpModule = module {
        factory { userCatalog  }
    }

    @Before
    fun setUp(){
        loadKoinModules(signUpModule)
    }

    @Test
    fun performSignUp(){
        launchSignUpScreen(signUpTestRule){
            typeEmail("ernest@friends.com")
            typePassword("Passw0rd!")
            submit()
        } verify {
            timelineScreenIsPresent()
        }
    }

    @Test
    fun displayDuplicateAccountError() {
        val signedUpUserEmail = "alice@email.com"
        val signedUserPassword = "@l1cePass"
        createUserWith(signedUpUserEmail, signedUserPassword)

        launchSignUpScreen(signUpTestRule) {
            typeEmail(signedUpUserEmail)
            typePassword(signedUserPassword)
            submit()
        } verify {
            duplicateAccountErrorIsShown()
        }
    }

    @After
    fun tearDown(){
        val resetModule = module {
            single { InMemoryUserCatalog() }
        }
        loadKoinModules(resetModule)
    }

    private fun createUserWith(
        signedUpUserEmail: String,
        signedUserPassword: String
    ) {
        userCatalog.createUser(signedUpUserEmail, signedUserPassword, "")
    }

}