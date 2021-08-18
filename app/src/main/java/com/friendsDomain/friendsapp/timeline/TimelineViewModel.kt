package com.friendsDomain.friendsapp.timeline

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.friendsDomain.friendsapp.app.CoroutineDispatchers
import com.friendsDomain.friendsapp.domain.timeline.TimelineRepository
import com.friendsDomain.friendsapp.timeline.state.TimelineState
import kotlinx.coroutines.launch

class TimelineViewModel(
    private val timelineRepository: TimelineRepository,
    private val dispatchers: CoroutineDispatchers
): ViewModel() {
    private val mutableTimelineState: MutableLiveData<TimelineState> =
        MutableLiveData<TimelineState>()

    val timelineState: LiveData<TimelineState> = mutableTimelineState

    fun timelineFor(userId: String) {
        viewModelScope.launch {
            mutableTimelineState.value = TimelineState.Loading
            mutableTimelineState.value = with(dispatchers.background) {
                timelineRepository.getTimelineFor(userId)
            }
        }
    }
}
