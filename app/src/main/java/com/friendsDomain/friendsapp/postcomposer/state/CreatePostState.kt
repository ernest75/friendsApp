package com.friendsDomain.friendsapp.postcomposer.state

import com.friendsDomain.friendsapp.domain.post.Post

sealed class CreatePostState {

    data class Created(val post: Post) : CreatePostState()
}
