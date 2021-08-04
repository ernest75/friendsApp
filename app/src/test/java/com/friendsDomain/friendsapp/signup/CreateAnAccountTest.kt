package com.friendsDomain.friendsapp.signup

import com.friendsDomain.friendsapp.InstantTaskExecutorExtension
import com.friendsDomain.friendsapp.domain.user.User
import com.friendsDomain.friendsapp.domain.validation.RegexCredentialsValidator
import com.friendsDomain.friendsapp.ui.signup.SignUpViewModel
import com.friendsDomain.friendsapp.ui.signup.state.SignUpState
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutorExtension::class)
class CreateAnAccountTest {

    @Test
    fun accountCreated() {
        val viewModel = SignUpViewModel(RegexCredentialsValidator())

        viewModel.createAccount("maya@friends.com","MaYa123@456","about maya")

        val maya = User("mayaId","maya@friends.com","about maya")
        assertEquals(SignUpState.SignedUp(maya),viewModel.signUpState.value)
    }
}