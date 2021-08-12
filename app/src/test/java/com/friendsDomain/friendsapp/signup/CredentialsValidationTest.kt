package com.friendsDomain.friendsapp.signup

import com.friendsDomain.friendsapp.InstantTaskExecutorExtension
import com.friendsDomain.friendsapp.domain.user.InMemoryUserCatalog
import com.friendsDomain.friendsapp.domain.user.UserRepository
import com.friendsDomain.friendsapp.domain.validation.CredentialsValidationResult
import com.friendsDomain.friendsapp.domain.validation.RegexCredentialsValidator
import com.friendsDomain.friendsapp.ui.signup.SignUpViewModel
import com.friendsDomain.friendsapp.ui.signup.state.SignUpState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

@ExtendWith(InstantTaskExecutorExtension::class)
class CredentialsValidationTest {

    @ParameterizedTest
    @CsvSource(
        "'email'",
        "'a@b.c'",
        "'ab@b.c'",
        "'ab@bc.c'",
        "''",
        "'   '",
    )
    fun invalidEmail(email: String) {
        val viewModel = SignUpViewModel(RegexCredentialsValidator(),
            UserRepository(InMemoryUserCatalog())
        )

        viewModel.createAccount(email,":password:",":about:")

        assertEquals(SignUpState.BadEmail,viewModel.signUpState.value)
    }
    
    @ParameterizedTest
    @CsvSource(
        "''",
        "'     '",
        "'12345678'",
        "'abcdDEF678'",
        "'abcd678#$'",
        "'ABCD678#$'",
    )
    fun invalidPassword(password: String) {
        val viewModel = SignUpViewModel(RegexCredentialsValidator(),
            UserRepository(InMemoryUserCatalog()))

        viewModel.createAccount("ernest@friemds.com", password,":about:")

        assertEquals(SignUpState.BadPassword,viewModel.signUpState.value)
    }
    
    @Test
    fun validCredentials() {
        val validator = RegexCredentialsValidator()

        val result = validator.validate("ernest@friemds.com", "aBc@123456")

        assertEquals(CredentialsValidationResult.Valid,result)

    }



}