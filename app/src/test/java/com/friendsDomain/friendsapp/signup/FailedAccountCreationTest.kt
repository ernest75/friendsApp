package com.friendsDomain.friendsapp.signup

import android.view.KeyCharacterMap
import com.friendsDomain.friendsapp.domain.exceptions.BackendException
import com.friendsDomain.friendsapp.domain.exceptions.ConnectionUnavailableException
import com.friendsDomain.friendsapp.domain.user.InMemoryUserCatalog
import com.friendsDomain.friendsapp.domain.user.User
import com.friendsDomain.friendsapp.domain.user.UserCatalog
import com.friendsDomain.friendsapp.domain.user.UserRepository
import com.friendsDomain.friendsapp.ui.signup.state.SignUpState
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class FailedAccountCreationTest {
    
    @Test
    fun backendError() = runBlocking {
        val userRepository = UserRepository(UnavailableUserCatalog())

        val result = userRepository.signUp(":email:",":password:",":about:")

        assertEquals(SignUpState.BackEndError,result)
    }
    
    @Test
    fun offlineError() = runBlocking {
        val userRepository = UserRepository(OfflineUserCatalog())

        val result = userRepository.signUp(":email:",":password:",":about:")

        assertEquals(SignUpState.Offline,result)
    }
}

class OfflineUserCatalog : UserCatalog {
    override suspend fun createUser(email: String, password: String, about: String): User {
        throw ConnectionUnavailableException()
    }

}

class UnavailableUserCatalog : UserCatalog {
    override suspend fun createUser(email: String, password: String, about: String): User {
        throw BackendException()
    }

}
