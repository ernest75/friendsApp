package com.friendsDomain.friendsapp.domain.user

import com.friendsDomain.friendsapp.domain.exceptions.DuplicateAccountException
import com.friendsDomain.friendsapp.ui.signup.state.SignUpState

class UserRepository(
    private val userCatalog: InMemoryUserCatalog
) {

    fun signUp(
        email: String,
        password: String,
        about: String
    ): SignUpState {
        return try {
            val user = userCatalog.createUser(email, password, about)
            SignUpState.SignedUp(user)
        } catch (duplicateAccount: DuplicateAccountException) {
            SignUpState.DuplicateAccount
        }
    }
}