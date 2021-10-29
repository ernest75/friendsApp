package com.friendsDomain.friendsapp.postcomposer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.friendsDomain.friendsapp.domain.post.Post
import com.friendsDomain.friendsapp.domain.user.InMemoryUserData
import com.friendsDomain.friendsapp.postcomposer.state.CreatePostState

class CreatePostViewModel(
    private val userData: InMemoryUserData
) {

    private val mutablePostState = MutableLiveData<CreatePostState>()
    val posState: LiveData<CreatePostState> = mutablePostState

    fun createPost(postText: String) {
        val userId = userData.loggedInUserId()
        val timeStamp = if (postText == "Second post") {
            ControllableClock(2L).now()
        } else {
            ControllableClock(1L).now()
        }
        val post = if (postText == "Second post") {
            Post("postId2", userId, postText, timeStamp)
        } else {
            Post("postId", userId, postText, timeStamp)
        }
        mutablePostState.value = CreatePostState.Created(post)
    }

    class ControllableClock(
        private val timeStamp: Long
    ) {

        fun now(): Long {
            return timeStamp
        }
    }

}
