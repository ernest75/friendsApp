package com.friendsDomain.friendsapp.postcomposer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.friendsDomain.friendsapp.domain.exceptions.BackendException
import com.friendsDomain.friendsapp.domain.exceptions.ConnectionUnavailableException
import com.friendsDomain.friendsapp.domain.post.Post
import com.friendsDomain.friendsapp.domain.user.InMemoryUserData
import com.friendsDomain.friendsapp.infrastructure.Clock
import com.friendsDomain.friendsapp.infrastructure.IdGenerator
import com.friendsDomain.friendsapp.postcomposer.state.CreatePostState

class CreatePostViewModel(
    private val userData: InMemoryUserData,
    private val clock: Clock,
    private val idGenerator: IdGenerator
) {

    private val mutablePostState = MutableLiveData<CreatePostState>()
    val postState: LiveData<CreatePostState> = mutablePostState

    fun createPost(postText: String) {
        val result = PostRepository().createNewPost(postText)
        mutablePostState.value = result
    }

    inner class PostRepository {

        fun createNewPost(postText: String): CreatePostState {
            return try {
                val post = addPost(userData.loggedInUserId(), postText)
                CreatePostState.Created(post)
            } catch (backendException: BackendException) {
                CreatePostState.BackEndError
            } catch (connectionUnavailableException: ConnectionUnavailableException) {
                CreatePostState.Offline
            }
        }

        fun addPost(userId: String, postText: String): Post {
            if (postText == ":backEnd:") {
                throw BackendException()
            } else if (postText == ":offline:") {
                throw ConnectionUnavailableException()
            }
            val timeStamp = clock.now()
            val postId = idGenerator.next()
            return Post(postId, userId, postText, timeStamp)
        }
    }

}
