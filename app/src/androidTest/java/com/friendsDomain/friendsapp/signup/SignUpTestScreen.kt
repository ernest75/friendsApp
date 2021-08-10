package com.friendsDomain.friendsapp.signup

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.friendsDomain.friendsapp.MainActivity
import com.friendsDomain.friendsapp.domain.exceptions.BackendException
import com.friendsDomain.friendsapp.domain.exceptions.ConnectionUnavailableException
import com.friendsDomain.friendsapp.domain.user.InMemoryUserCatalog
import com.friendsDomain.friendsapp.domain.user.User
import com.friendsDomain.friendsapp.domain.user.UserCatalog
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
        factory <UserCatalog>{ userCatalog  }
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
    fun displayBadEmailError(){
        launchSignUpScreen(signUpTestRule){
            typeEmail("badEmail")
            typePassword("Passw0rd!")
            submit()
        } verify{
            badEmailErrorIsShown()
        }
    }

    @Test
    fun displayBadPasswordError(){
        launchSignUpScreen(signUpTestRule){
            typeEmail("ernest@friends.com")
            typePassword("badPassword")
            submit()
        } verify{
            badPasswordErrorIsShown()
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

    @Test
    fun displayBackEndError(){
        replaceUserCatalogWith(UnavailableUserCatalog())
        launchSignUpScreen(signUpTestRule){
            typeEmail("joe@friends.com")
            typePassword("J0ePass!")
            submit()
        } verify {
            backEndErrorIsShown()
        }

    }

    @Test
    fun displayOfflineError() {
        replaceUserCatalogWith(OfflineUserCatalog())

        launchSignUpScreen(signUpTestRule) {
            typeEmail("ernest@friends.com")
            typePassword("Ern3stPass!")
            submit()
        } verify {
            offlineErrorIsShown()
        }
    }

    private fun replaceUserCatalogWith(userCatalog: UserCatalog) {
        val replaceModule = module {
            factory { userCatalog }
        }
        loadKoinModules(replaceModule)
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

    class UnavailableUserCatalog : UserCatalog {

        override fun createUser(email: String, password: String, about: String): User {
            throw BackendException()
        }

    }

    class OfflineUserCatalog : UserCatalog {
        override fun createUser(email: String, password: String, about: String): User {
            throw ConnectionUnavailableException()
        }

    }

}