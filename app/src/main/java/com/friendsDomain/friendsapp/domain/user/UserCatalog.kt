package com.friendsDomain.friendsapp.domain.user

interface UserCatalog {

    fun createUser(
        email: String,
        password: String,
        about: String
    ): User
}