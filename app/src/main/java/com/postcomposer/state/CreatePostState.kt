package com.postcomposer.state

import com.friendsDomain.friendsapp.domain.post.Post

sealed class CreatePostState {

    data class Created(val post: Post) : CreatePostState()
}
