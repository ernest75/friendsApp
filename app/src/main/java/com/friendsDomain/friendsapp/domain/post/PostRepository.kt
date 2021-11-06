package com.friendsDomain.friendsapp.domain.post

import com.friendsDomain.friendsapp.domain.exceptions.BackendException
import com.friendsDomain.friendsapp.domain.exceptions.ConnectionUnavailableException
import com.friendsDomain.friendsapp.domain.user.InMemoryUserData
import com.friendsDomain.friendsapp.infrastructure.Clock
import com.friendsDomain.friendsapp.infrastructure.IdGenerator
import com.friendsDomain.friendsapp.postcomposer.state.CreatePostState

class PostRepository(
    private val userData: InMemoryUserData,
    private val clock: Clock,
    private val idGenerator: IdGenerator
) {

    fun createNewPost(postText: String): CreatePostState {
        return try {
            val post = InMemoryPostCatalog(
                idGenerator = idGenerator,
                clock = clock
            ).addPost(userData.loggedInUserId(), postText)
            CreatePostState.Created(post)
        } catch (backendException: BackendException) {
            CreatePostState.BackEndError
        } catch (connectionUnavailableException: ConnectionUnavailableException) {
            CreatePostState.Offline
        }
    }
}
