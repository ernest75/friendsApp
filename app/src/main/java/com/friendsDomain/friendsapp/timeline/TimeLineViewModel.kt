package com.friendsDomain.friendsapp.timeline

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.friendsDomain.friendsapp.domain.post.Post
import com.friendsDomain.friendsapp.domain.user.Following
import com.friendsDomain.friendsapp.timeline.state.TimelineState

class TimeLineViewModel {
    private val mutableTimelineState: MutableLiveData<TimelineState> =
        MutableLiveData<TimelineState>()

    val timelineState: LiveData<TimelineState> = mutableTimelineState

    fun timelineFor(userId: String) {
        val followings = listOf(
            Following("saraId", "lucyId"),
            Following("annaId", "lucyId")
        )
        val userIds = listOf(userId) + followings
            .filter { it.userId == userId }
            .map { it.followedId }

        val postsForUser = InMemoryPostCatalog().postsFor(userIds)
        mutableTimelineState.value = TimelineState.Posts(postsForUser)
    }

    class InMemoryPostCatalog{

        fun postsFor(userIds: List<String>): List<Post> {
            val availablePosts = listOf(
                Post("postId", "timId", "post text", 1L),
                Post("post2", "lucyId", "post 2", 2L),
                Post("post1", "lucyId", "post 1", 1L),
                Post("post4", "saraId", "post 4", 4L),
                Post("post3", "saraId", "post 3", 3L)
            )
            return availablePosts.filter { userIds.contains(it.userId) }
        }
    }

}
