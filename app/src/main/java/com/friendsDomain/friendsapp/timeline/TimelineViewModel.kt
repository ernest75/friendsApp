package com.friendsDomain.friendsapp.timeline

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.friendsDomain.friendsapp.domain.timeline.TimelineRepository
import com.friendsDomain.friendsapp.timeline.state.TimelineState

class TimelineViewModel(
    private val timelineRepository: TimelineRepository
): ViewModel() {
    private val mutableTimelineState: MutableLiveData<TimelineState> =
        MutableLiveData<TimelineState>()

    val timelineState: LiveData<TimelineState> = mutableTimelineState

    fun timelineFor(userId: String) {
        mutableTimelineState.value = TimelineState.Loading
        val result = timelineRepository
            .getTimelineFor(userId)
        mutableTimelineState.value = result
    }
}
