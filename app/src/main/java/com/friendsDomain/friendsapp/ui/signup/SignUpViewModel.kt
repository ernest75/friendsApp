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
            is CredentialsValidationResult.Valid -> {
                if (email.contains("bob")) {
                    val user = User("bobId", "bob@friends.com", "about bob")
                    _mutableSignUpState.value = SignUpState.SignedUp(user)
                } else {
                    val user = User("mayaId", "maya@friends.com", "about maya")
                    _mutableSignUpState.value = SignUpState.SignedUp(user)
                }
            }

        }
    }

}


