package com.friendsDomain.friendsapp.domain.post

interface PostCatalog {
    suspend fun postsFor(userIds: List<String>): List<Post>
}