package com.friendsDomain.friendsapp.postcomposer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    val posState: LiveData<CreatePostState> = mutablePostState

    fun createPost(postText: String) {
        val post = createNewPost(postText)
        mutablePostState.value = CreatePostState.Created(post)
    }

    private fun createNewPost(postText: String): Post {
        val userId = userData.loggedInUserId()
        val timeStamp = clock.now()
        val postId = idGenerator.next()
        return Post(postId, userId, postText, timeStamp)
    }

}
