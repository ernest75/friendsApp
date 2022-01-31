package com.friendsDomain.friendsapp.domain.user

import com.friendsDomain.friendsapp.domain.exceptions.BackendException

class UnavailableUserCatalog : UserCatalog {
    override suspend fun createUser(email: String, password: String, about: String): User {
        throw BackendException()
    }

    override fun followedBy(userId: String): List<String> {
        throw BackendException()
    }

}