package com.friendsDomain.friendsapp.timeline

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.friendsDomain.friendsapp.domain.exceptions.BackendException
import com.friendsDomain.friendsapp.domain.exceptions.ConnectionUnavailableException
import com.friendsDomain.friendsapp.domain.post.PostCatalog
import com.friendsDomain.friendsapp.domain.timeline.TimelineRepository
import com.friendsDomain.friendsapp.domain.user.UserCatalog
import com.friendsDomain.friendsapp.timeline.state.TimelineState

class TimeLineViewModel(
    private val userCatalog: UserCatalog,
    private val postCatalog: PostCatalog
) {
    private val mutableTimelineState: MutableLiveData<TimelineState> =
        MutableLiveData<TimelineState>()

    val timelineState: LiveData<TimelineState> = mutableTimelineState

    fun timelineFor(userId: String) {
        val result = TimelineRepository(userCatalog,postCatalog)
            .getTimelineFor(userId)
        mutableTimelineState.value = result
    }
}
