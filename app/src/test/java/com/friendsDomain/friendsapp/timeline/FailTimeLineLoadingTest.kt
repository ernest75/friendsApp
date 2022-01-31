package com.friendsDomain.friendsapp.timeline

import com.friendsDomain.friendsapp.InstantTaskExecutorExtension
import com.friendsDomain.friendsapp.app.TestDispatchers
import com.friendsDomain.friendsapp.domain.post.OfflinePostCatalog
import com.friendsDomain.friendsapp.domain.post.UnavailablePostCatalog
import com.friendsDomain.friendsapp.domain.timeline.TimelineRepository
import com.friendsDomain.friendsapp.domain.user.InMemoryUserCatalog
import com.friendsDomain.friendsapp.timeline.state.TimelineState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutorExtension::class)
class FailTimeLineLoadingTest {

    @Test
    fun backendError() {
        val userCatalog = InMemoryUserCatalog()
        val postCatalog = UnavailablePostCatalog()
        val viewModel = TimelineViewModel(
            TimelineRepository(userCatalog, postCatalog),
            TestDispatchers()
        )

        viewModel.timelineFor(":irrelevant:")

        assertEquals(TimelineState.BackendError, viewModel.timelineState.value)
    }

    @Test
    fun offlineError() {
        val userCatalog = InMemoryUserCatalog()
        val postCatalog = OfflinePostCatalog()
        val viewModel = TimelineViewModel(
            TimelineRepository(userCatalog, postCatalog),
            TestDispatchers()
        )

        viewModel.timelineFor(":irrelevant:")

        assertEquals(TimelineState.OfflineError, viewModel.timelineState.value)
    }
}