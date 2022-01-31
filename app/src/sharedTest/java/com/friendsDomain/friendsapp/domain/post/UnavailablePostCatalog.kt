package com.friendsDomain.friendsapp.domain.post

import com.friendsDomain.friendsapp.domain.exceptions.BackendException

class UnavailablePostCatalog : PostCatalog {
    override suspend fun addPost(userId: String, postText: String): Post {
        throw BackendException()
    }

    override suspend fun postsFor(userIds: List<String>): List<Post> {
        throw BackendException()
    }
}

