package com.friendsDomain.friendsapp.postcomposer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.friendsDomain.friendsapp.domain.post.PostRepository
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
        val result = PostRepository(userData, clock, idGenerator)
            .createNewPost(postText)
        mutablePostState.value = result
    }

}
