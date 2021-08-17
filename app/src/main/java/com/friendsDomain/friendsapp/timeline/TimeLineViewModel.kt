package com.friendsDomain.friendsapp.timeline

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.friendsDomain.friendsapp.domain.timeline.TimelineRepository
import com.friendsDomain.friendsapp.timeline.state.TimelineState

class TimeLineViewModel(
    private val timelineRepository: TimelineRepository
) {
    private val mutableTimelineState: MutableLiveData<TimelineState> =
        MutableLiveData<TimelineState>()

    val timelineState: LiveData<TimelineState> = mutableTimelineState

    fun timelineFor(userId: String) {
        val result = timelineRepository
            .getTimelineFor(userId)
        mutableTimelineState.value = result
    }
}
