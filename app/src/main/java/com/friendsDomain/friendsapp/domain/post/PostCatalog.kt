package com.friendsDomain.friendsapp.domain.post

interface PostCatalog {
    fun postsFor(userIds: List<String>): List<Post>
}