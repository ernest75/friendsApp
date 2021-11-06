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
    val postState: LiveData<CreatePostState> = mutablePostState

    fun createPost(postText: String) {
        if (postText == ":backEnd:"){
            mutablePostState.value = CreatePostState.BackEndError
        } else if(postText == ":offline:"){
            mutablePostState.value = CreatePostState.Offline
        }else {
            val post = addPost(userData.loggedInUserId(), postText)
            mutablePostState.value = CreatePostState.Created(post)
        }
    }

    private fun addPost(userId: String, postText: String): Post {
        val timeStamp = clock.now()
        val postId = idGenerator.next()
        return Post(postId, userId, postText, timeStamp)
    }

}
