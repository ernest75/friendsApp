package com.friendsDomain.friendsapp.ui.signup.state

import com.friendsDomain.friendsapp.domain.user.User

sealed class SignUpState {
    data class SignedUp(val user: User): SignUpState()

    object BadEmail: SignUpState()

    object BadPassword: SignUpState()

    object DuplicateAccount :SignUpState()

}
