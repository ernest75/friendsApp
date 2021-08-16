package com.friendsDomain.friendsapp.timeline

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.friendsDomain.friendsapp.domain.post.InMemoryPostCatalog
import com.friendsDomain.friendsapp.domain.user.Following
import com.friendsDomain.friendsapp.domain.user.InMemoryUserCatalog
import com.friendsDomain.friendsapp.timeline.state.TimelineState

class TimeLineViewModel {
    private val mutableTimelineState: MutableLiveData<TimelineState> =
        MutableLiveData<TimelineState>()

    val timelineState: LiveData<TimelineState> = mutableTimelineState

    fun timelineFor(userId: String) {
        val userIds = listOf(userId) + InMemoryUserCatalog().followedBy(userId)
        val postsForUser = InMemoryPostCatalog().postsFor(userIds)
        mutableTimelineState.value = TimelineState.Posts(postsForUser)
    }

}
