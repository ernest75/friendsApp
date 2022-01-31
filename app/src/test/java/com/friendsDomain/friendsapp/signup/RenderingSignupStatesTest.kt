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
class RenderingSignupStatesTest {

    private val userRepository = UserRepository(InMemoryUserCatalog())
    private val viewModel = SignUpViewModel(
        RegexCredentialsValidator(),
        userRepository,
        TestDispatchers()
    )
    private val tom = User("tomId", "tom@friends.com", "about Tom")

    @Test
    fun uiStatesAreDeliveredInParticularOrder() {
        val deliveredStates = mutableListOf<SignUpState>()
        viewModel.signUpState.observeForever { deliveredStates.add(it) }

        viewModel.createAccount(tom.email, "P@sswword1233", tom.about)

        assertEquals(
            listOf(SignUpState.Loading, SignUpState.SignedUp(tom)),
            deliveredStates
        )
    }
}