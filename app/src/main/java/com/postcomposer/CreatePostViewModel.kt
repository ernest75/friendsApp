package com.postcomposer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.friendsDomain.friendsapp.domain.post.Post
import com.postcomposer.state.CreatePostState

class CreatePostViewModel {

    private val mutablePostState = MutableLiveData<CreatePostState>()
    val posState: LiveData<CreatePostState> = mutablePostState

    fun createPost(postText: String) {
        val userId = loggedInUserId()
        val post = if (postText ==  "Second post"){
            Post("postId2", userId, postText,2L)
        } else {
            Post("postId", userId,postText,1L)
        }
        mutablePostState.value = CreatePostState.Created(post)
    }

    private fun loggedInUserId() = "userId"

}
