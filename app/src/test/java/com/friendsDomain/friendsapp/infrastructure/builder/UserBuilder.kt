package com.friendsDomain.friendsapp.infrastructure.builder

import com.friendsDomain.friendsapp.domain.user.User
import java.util.*

class UserBuilder {
    private var userId = UUID.randomUUID().toString()
    private var userEmail = "user@friends.com"
    private var aboutUser = "About user"

    companion object {
        fun aUser(): UserBuilder {
            return UserBuilder()
        }
    }

    fun withId(id: String): UserBuilder = this.apply {
        userId = id
    }

    fun build(): User {
        return User(userId, userEmail, aboutUser)
    }
}