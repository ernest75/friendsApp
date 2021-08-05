package com.friendsDomain.friendsapp.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.friendsDomain.friendsapp.domain.user.User
import com.friendsDomain.friendsapp.domain.validation.CredentialsValidationResult
import com.friendsDomain.friendsapp.domain.validation.RegexCredentialsValidator
import com.friendsDomain.friendsapp.ui.signup.state.SignUpState

class SignUpViewModel(
    private val credentialsValidator: RegexCredentialsValidator
    ) {

    private val _mutableSignUpState = MutableLiveData<SignUpState>()
    val signUpState: LiveData<SignUpState> = _mutableSignUpState

    fun createAccount(
        email: String,
        password: String,
        about: String
    ) {
        when (credentialsValidator.validate(email, password)) {
            is CredentialsValidationResult.InvalidEmail ->
                _mutableSignUpState.value = SignUpState.BadEmail
            is CredentialsValidationResult.InvalidPassword ->
                _mutableSignUpState.value = SignUpState.BadPassword
            is CredentialsValidationResult.Valid ->
                _mutableSignUpState.value = signUp(email, password, about)
        }
    }

    private val userCatalog = InMemoryUserCatalog()

    private fun signUp(
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

    class InMemoryUserCatalog(private val usersForPassword: MutableMap<String, MutableList<User>> = mutableMapOf()
    ) {
        fun createUser(
            email: String,
            password: String,
            about: String
        ): User {
            checkAccountExists(email)
            val userId = createUserIdFor(email)
            val user = User(userId, email, about)
            saveUser(password, user)
            return user
        }

        private fun saveUser(
            password: String,
            user: User
        ) {
            usersForPassword.getOrPut(password, ::mutableListOf).add(user)
        }

        private fun createUserIdFor(email: String): String {
            val userId = email.takeWhile { it != '@' } + "Id"
            return userId
        }

        private fun checkAccountExists(email: String) {
            if (usersForPassword.values.flatten().any { it.email == email }) {
                throw DuplicateAccountException()
            }
        }

    }

    private fun createUser(
        email: String,
        password: String,
        about: String
    ): User {
        checkAccountExists(email)
        val userId = createUserIdFor(email)
        val user = User(userId, email, about)
        saveUser(password, user)
        return user
    }

    private fun saveUser(
        password: String,
        user: User
    ) {
        usersForPassword.getOrPut(password, ::mutableListOf).add(user)
    }

    private fun createUserIdFor(email: String): String {
        val userId = email.takeWhile { it != '@' } + "Id"
        return userId
    }

    private fun checkAccountExists(email: String) {
        if (usersForPassword.values.flatten().any { it.email == email }) {
            throw DuplicateAccountException()
        }
    }

    private val usersForPassword = mutableMapOf<String, MutableList<User>>()


    class DuplicateAccountException : Throwable()
}


