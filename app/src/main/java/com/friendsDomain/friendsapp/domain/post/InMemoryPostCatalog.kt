package com.friendsDomain.friendsapp.domain.post

import com.friendsDomain.friendsapp.domain.exceptions.ConnectionUnavailableException
import com.friendsDomain.friendsapp.infrastructure.Clock
import com.friendsDomain.friendsapp.infrastructure.IdGenerator
import com.friendsDomain.friendsapp.infrastructure.SystemClock
import com.friendsDomain.friendsapp.infrastructure.UUIDGenerator

class InMemoryPostCatalog(
    private val availablePosts: List<Post> = emptyList(),
    private val idGenerator: IdGenerator = UUIDGenerator(),
    private val clock: Clock = SystemClock()
) : PostCatalog {

    override suspend fun addPost(userId: String, postText: String): Post {
        val timeStamp = clock.now()
        val postId = idGenerator.next()
        return Post(postId, userId, postText, timeStamp)
    }

    override suspend fun postsFor(userIds: List<String>): List<Post> {
        return availablePosts.filter { userIds.contains(it.userId) }
    }
}