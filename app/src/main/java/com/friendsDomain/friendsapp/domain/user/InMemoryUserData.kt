package com.friendsDomain.friendsapp.domain.user

class InMemoryUserData(
    private val loggedInUserID: String
) {
    fun loggedInUserId() = loggedInUserID
}