package com.friendsDomain.friendsapp.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.friendsDomain.friendsapp.app.TestDispatchers
import com.friendsDomain.friendsapp.domain.user.UserRepository
import com.friendsDomain.friendsapp.domain.validation.CredentialsValidationResult
import com.friendsDomain.friendsapp.domain.validation.RegexCredentialsValidator
import com.friendsDomain.friendsapp.ui.signup.state.SignUpState
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpViewModel(
    private val credentialsValidator: RegexCredentialsValidator,
    private val userRepository: UserRepository,
    private val dispatchers: TestDispatchers
): ViewModel() {

    private val mutableSignUpState = MutableLiveData<SignUpState>()
    val signUpState: LiveData<SignUpState> = mutableSignUpState

    fun createAccount(
        email: String,
        password: String,
        about: String
    ) {
        when (credentialsValidator.validate(email, password)) {
            is CredentialsValidationResult.InvalidEmail ->
                mutableSignUpState.value = SignUpState.BadEmail
            is CredentialsValidationResult.InvalidPassword ->
                mutableSignUpState.value = SignUpState.BadPassword
            is CredentialsValidationResult.Valid ->
                proceedWithSignedUp(email, password, about)
        }
    }

    private fun proceedWithSignedUp(
        email: String,
        password: String,
        about: String
    ) {
        viewModelScope.launch {
            mutableSignUpState.value = SignUpState.Loading
            mutableSignUpState.value = withContext(dispatchers.background) {
                userRepository.signUp(email, password, about)
            }
        }
    }

}


