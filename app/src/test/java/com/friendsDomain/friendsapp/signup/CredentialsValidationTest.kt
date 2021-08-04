package com.friendsDomain.friendsapp.signup

import com.friendsDomain.friendsapp.InstantTaskExecutorExtension
import com.friendsDomain.friendsapp.ui.signup.SignUpViewModel
import com.friendsDomain.friendsapp.ui.signup.state.SignUpState
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutorExtension::class)
class CredentialsValidationTest {

    @Test
    fun invalidEmail() {
        val viewModel = SignUpViewModel()

        viewModel.createAccount("email",":password:",":about:")

        assertEquals(SignUpState.BadEmail,viewModel.signUpState.value)
    }


}