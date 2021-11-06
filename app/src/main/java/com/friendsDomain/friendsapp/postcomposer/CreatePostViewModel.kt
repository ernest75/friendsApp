package com.friendsDomain.friendsapp.postcomposer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.friendsDomain.friendsapp.domain.post.PostRepository
import com.friendsDomain.friendsapp.postcomposer.state.CreatePostState

class CreatePostViewModel(
    private val postRepository: PostRepository
) {

    private val mutablePostState = MutableLiveData<CreatePostState>()
    val postState: LiveData<CreatePostState> = mutablePostState

    fun createPost(postText: String) {
        val result = postRepository
            .createNewPost(postText)
        mutablePostState.value = result
    }

}
