package com.friendsDomain.friendsapp.timeline.state

import com.friendsDomain.friendsapp.domain.post.Post

sealed class TimelineState {
    data class Posts(val postList: List<Post>): TimelineState()

}
