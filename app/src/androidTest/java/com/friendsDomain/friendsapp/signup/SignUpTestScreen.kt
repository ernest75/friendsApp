package com.friendsDomain.friendsapp.signup

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.friendsDomain.friendsapp.MainActivity
import com.friendsDomain.friendsapp.domain.user.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

class SignUpScreenTest {

    @get:Rule
    val signUpTestRule = createAndroidComposeRule<MainActivity>()

    private val signUpModule = module {
        factory<UserCatalog> { InMemoryUserCatalog() }
    }

    @Before
    fun setUp() {
        loadKoinModules(signUpModule)
    }

    @Test
    fun performSignUp() {
        launchSignUpScreen(signUpTestRule) {
            typeEmail("ernest@friends.com")
            typePassword("Passw0rd!")
            submit()
        } verify {
            timelineScreenIsPresent()
        }
    }

    @Test
    fun displayBadEmailError() {
        launchSignUpScreen(signUpTestRule) {
            typeEmail("badEmail")
            typePassword("Passw0rd!")
            submit()
        } verify {
            badEmailErrorIsShown()
        }
    }

    @Test
    fun resetBadEmailError() {
        launchSignUpScreen(signUpTestRule) {
            typeEmail("badEmail")
            typePassword("Passw0rd!")
            submit()
            typeEmail("email@friends.com")
        } verify {
            badEmailErrorIsNotShown()
        }
    }

    @Test
    fun displayBadPasswordError() {
        launchSignUpScreen(signUpTestRule) {
            typeEmail("ernest@friends.com")
            typePassword("badPassword")
            submit()
        } verify {
            badPasswordErrorIsShown()
        }
    }

    @Test
    fun resetBadPasswordError() {
        launchSignUpScreen(signUpTestRule) {
            typeEmail("ernest@friends.com")
            typePassword("badPassword")
            submit()
            typePassword("Passw0rd!")
        } verify {
            badPasswordErrorIsNotShown()
        }
    }

    @Test
    fun displayDuplicateAccountError() = runBlocking<Unit> {
        val signedUpUserEmail = "alice@email.com"
        val signedUserPassword = "@l1cePass"
        replaceUserCatalogWith(InMemoryUserCatalog().apply {
            createUser(signedUpUserEmail, signedUserPassword, "")
        })

        launchSignUpScreen(signUpTestRule) {
            typeEmail(signedUpUserEmail)
            typePassword(signedUserPassword)
            submit()
        } verify {
            duplicateAccountErrorIsShown()
        }
    }

    @Test
    fun displayBackEndError() = runBlocking<Unit> {
        replaceUserCatalogWith(UnavailableUserCatalog())
        launchSignUpScreen(signUpTestRule) {
            typeEmail("joe@friends.com")
            typePassword("J0ePass!")
            submit()
        } verify {
            backEndErrorIsShown()
        }

    }

    @Test
    fun displayOfflineError() = runBlocking<Unit> {
        replaceUserCatalogWith(OfflineUserCatalog())

        launchSignUpScreen(signUpTestRule) {
            typeEmail("ernest@friends.com")
            typePassword("Ern3stPass!")
            submit()
        } verify {
            offlineErrorIsShown()
        }
    }

    @Test
    fun displayBlockingLoading() {
        replaceUserCatalogWith(DelayingUserCatalog())
        launchSignUpScreen(signUpTestRule) {
            typeEmail("ernest@friends.com")
            typePassword("Ern3stPass!")
            submit()
        } verify {
            blockingLoadingIsShown()
        }
    }

    @After
    fun tearDown() {
        replaceUserCatalogWith(InMemoryUserCatalog())
    }

    private fun replaceUserCatalogWith(userCatalog: UserCatalog) {
        val replaceModule = module {
            factory { userCatalog }
        }
        loadKoinModules(replaceModule)
    }

    class DelayingUserCatalog : UserCatalog {
        override suspend fun createUser(email: String, password: String, about: String): User {
            delay(1000)
            return User("SomeID", email, about)
        }

        override fun followedBy(userId: String): List<String> {
            TODO("Not yet implemented")
        }

    }
}
