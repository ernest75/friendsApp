package com.friendsDomain.friendsapp.timeline

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.friendsDomain.friendsapp.domain.post.Post
import com.friendsDomain.friendsapp.timeline.state.TimelineState

class TimeLineViewModel {
    private val mutableTimelineState: MutableLiveData<TimelineState> =
        MutableLiveData<TimelineState>()

    val timelineState: LiveData<TimelineState> = mutableTimelineState

    fun timelineFor(userId: String) {
        if(userId=="timId"){
            val posts = listOf(Post("postId", "timId", "post text", 1L))
            mutableTimelineState.value = TimelineState.Posts(posts)
        }else{
            mutableTimelineState.value = TimelineState.Posts(emptyList())
        }
    }
}
