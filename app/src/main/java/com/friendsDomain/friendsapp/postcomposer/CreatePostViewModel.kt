package com.friendsDomain.friendsapp.postcomposer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.friendsDomain.friendsapp.domain.post.Post
import com.friendsDomain.friendsapp.domain.user.InMemoryUserData
import com.friendsDomain.friendsapp.infrastructure.Clock
import com.friendsDomain.friendsapp.postcomposer.state.CreatePostState

class CreatePostViewModel(
    private val userData: InMemoryUserData,
    private val clock: Clock
) {

    private val mutablePostState = MutableLiveData<CreatePostState>()
    val posState: LiveData<CreatePostState> = mutablePostState

    fun createPost(postText: String) {
        val userId = userData.loggedInUserId()
        val timeStamp = clock.now()
        val post = if (postText == "Second post") {
            Post("postId2", userId, postText, timeStamp)
        } else {
            Post("postId", userId, postText, timeStamp)
        }
        mutablePostState.value = CreatePostState.Created(post)
    }

}
