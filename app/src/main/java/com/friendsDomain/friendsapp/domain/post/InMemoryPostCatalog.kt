package com.friendsDomain.friendsapp.domain.post

class InMemoryPostCatalog(
    private val availablePosts: List<Post>
    ) : PostCatalog {

    override fun postsFor(userIds: List<String>): List<Post> {
        return availablePosts.filter { userIds.contains(it.userId) }
    }
}