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
        val availablePosts = listOf(
            Post("postId", "timId", "post text", 1L),
            Post("post2", "lucyId", "post 2", 2L),
            Post("post1", "lucyId", "post 1", 1L)
        )
        if (userId=="annaId"){
            val annaPosts = availablePosts.filter { it.userId=="lucyId" }
            mutableTimelineState.value = TimelineState.Posts(annaPosts)
        }
        else if(userId=="timId"){
            val timPosts = availablePosts.filter { it.userId=="timId" }
            mutableTimelineState.value = TimelineState.Posts(timPosts)
        }else{
            mutableTimelineState.value = TimelineState.Posts(emptyList())
        }
    }
}
