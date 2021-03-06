package com.friendsDomain.friendsapp.timeline.state

import com.friendsDomain.friendsapp.domain.post.Post

sealed class TimelineState {

    data class Posts(val posts: List<Post>): TimelineState()

    object BackendError: TimelineState()

    object OfflineError: TimelineState()

    object Loading: TimelineState()

}
