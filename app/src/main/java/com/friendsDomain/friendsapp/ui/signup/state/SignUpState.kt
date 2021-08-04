package com.friendsDomain.friendsapp.ui.signup.state

sealed class SignUpState {
    object BadEmail: SignUpState()
    object BadPassword: SignUpState()

}
