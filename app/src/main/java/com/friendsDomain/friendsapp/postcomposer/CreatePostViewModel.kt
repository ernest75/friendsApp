package com.friendsDomain.friendsapp.postcomposer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.friendsDomain.friendsapp.app.CoroutineDispatchers
import com.friendsDomain.friendsapp.domain.post.PostRepository
import com.friendsDomain.friendsapp.postcomposer.state.CreatePostState
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreatePostViewModel(
    private val postRepository: PostRepository,
    private val dispatchers: CoroutineDispatchers
) : ViewModel() {

    private val mutablePostState = MutableLiveData<CreatePostState>()
    val postState: LiveData<CreatePostState> = mutablePostState

    fun createPost(postText: String) {
        viewModelScope.launch {
            mutablePostState.value = CreatePostState.Loading
            val result = withContext(dispatchers.background) {
                postRepository
                    .createNewPost(postText)

            }
            mutablePostState.value = result
        }
    }
}
