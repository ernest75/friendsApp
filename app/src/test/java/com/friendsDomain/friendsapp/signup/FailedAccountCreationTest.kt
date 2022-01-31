package com.friendsDomain.friendsapp.signup

import com.friendsDomain.friendsapp.domain.user.OfflineUserCatalog
import com.friendsDomain.friendsapp.domain.user.UnavailableUserCatalog
import com.friendsDomain.friendsapp.domain.user.UserRepository
import com.friendsDomain.friendsapp.ui.signup.state.SignUpState
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FailedAccountCreationTest {

    @Test
    fun backendError() = runBlocking {
        val userRepository = UserRepository(UnavailableUserCatalog())

        val result = userRepository.signUp(":email:", ":password:", ":about:")

        assertEquals(SignUpState.BackEndError, result)
    }

    @Test
    fun offlineError() = runBlocking {
        val userRepository = UserRepository(OfflineUserCatalog())

        val result = userRepository.signUp(":email:", ":password:", ":about:")

        assertEquals(SignUpState.Offline, result)
    }
}
