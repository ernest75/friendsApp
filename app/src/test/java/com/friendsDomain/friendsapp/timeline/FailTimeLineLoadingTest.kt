package com.friendsDomain.friendsapp.timeline

import com.friendsDomain.friendsapp.InstantTaskExecutorExtension
import com.friendsDomain.friendsapp.domain.exceptions.BackendException
import com.friendsDomain.friendsapp.domain.post.InMemoryPostCatalog
import com.friendsDomain.friendsapp.domain.post.Post
import com.friendsDomain.friendsapp.domain.post.PostCatalog
import com.friendsDomain.friendsapp.domain.user.InMemoryUserCatalog
import com.friendsDomain.friendsapp.timeline.state.TimelineState
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutorExtension::class)
class FailTimeLineLoadingTest {
    
    @Test
    fun backendError() {
        val userCatalog = InMemoryUserCatalog()
        val postCatalog = UnavailablePostCatalog(emptyList())
        val viewModel = TimeLineViewModel(userCatalog, postCatalog)

        viewModel.timelineFor(":irrelevant:")

        assertEquals(TimelineState.BackendError,viewModel.timelineState.value )
    }

    private class UnavailablePostCatalog(emptyList: List<Post>) :
        PostCatalog {
        override fun postsFor(userIds: List<String>): List<Post> {
            throw BackendException()
        }

    }
}