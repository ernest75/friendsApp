package com.friendsDomain.friendsapp.signup

import com.friendsDomain.friendsapp.InstantTaskExecutorExtension
import com.friendsDomain.friendsapp.app.TestDispatchers
import com.friendsDomain.friendsapp.domain.user.InMemoryUserCatalog
import com.friendsDomain.friendsapp.domain.user.User
import com.friendsDomain.friendsapp.domain.user.UserRepository
import com.friendsDomain.friendsapp.domain.validation.RegexCredentialsValidator
import com.friendsDomain.friendsapp.ui.signup.SignUpViewModel
import com.friendsDomain.friendsapp.ui.signup.state.SignUpState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutorExtension::class)
class CreateAnAccountTest {

    private val credentialsValidator = RegexCredentialsValidator()
    private val viewModel = SignUpViewModel(
        credentialsValidator,
        UserRepository(InMemoryUserCatalog()),
        TestDispatchers()
    )

    @Test
    fun accountCreated() {
        val maya = User("mayaId", "maya@friends.com", "about maya")


        viewModel.createAccount(maya.email, "MaYa123@456", maya.about)

        assertEquals(SignUpState.SignedUp(maya), viewModel.signUpState.value)
    }

    @Test
    fun anotherAccountCreated() {
        val bob = User("bobId", "bob@friends.com", "about bob")

        viewModel.createAccount(bob.email, "Pas123$%564", bob.about)

        assertEquals(SignUpState.SignedUp(bob), viewModel.signUpState.value)
    }

    @Test
    fun createDuplicateAccount() {
        val anna = User("annaId", "anna@friends.com", "about Anna")
        val password = "AnnaPass12$$"
        val usersForPassword = mutableMapOf(password to mutableListOf(anna))
        val userRepository = UserRepository(InMemoryUserCatalog(usersForPassword))
        val viewModel = SignUpViewModel(credentialsValidator, userRepository, TestDispatchers())

        viewModel.createAccount(anna.email, password, anna.about)

        assertEquals(SignUpState.DuplicateAccount, viewModel.signUpState.value)
    }


}