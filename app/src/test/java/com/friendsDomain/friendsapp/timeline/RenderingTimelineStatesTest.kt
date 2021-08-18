package com.friendsDomain.friendsapp.timeline

import com.friendsDomain.friendsapp.InstantTaskExecutorExtension
import com.friendsDomain.friendsapp.app.DefaultDispatchers
import com.friendsDomain.friendsapp.app.TestDispatchers
import com.friendsDomain.friendsapp.domain.post.InMemoryPostCatalog
import com.friendsDomain.friendsapp.domain.timeline.TimelineRepository
import com.friendsDomain.friendsapp.domain.user.InMemoryUserCatalog
import com.friendsDomain.friendsapp.timeline.state.TimelineState
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutorExtension::class)
class RenderingTimelineStatesTest {
    val timelineRepository = TimelineRepository(
        InMemoryUserCatalog(),
        InMemoryPostCatalog()
    )
    val viewModel = TimelineViewModel(timelineRepository, TestDispatchers())

    @Test
    fun timelineStatesExposedToAnObserver() {
        val renderedStates = mutableListOf<TimelineState>()
        viewModel.timelineState.observeForever{ renderedStates.add(it)}

        viewModel.timelineFor(":irrelevantId:")

        assertEquals(
            listOf(TimelineState.Loading, TimelineState.Posts(emptyList())),
            renderedStates
        )
    }
}
