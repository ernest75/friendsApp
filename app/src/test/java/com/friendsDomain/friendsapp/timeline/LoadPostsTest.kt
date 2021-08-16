package com.friendsDomain.friendsapp.timeline

import com.friendsDomain.friendsapp.InstantTaskExecutorExtension
import com.friendsDomain.friendsapp.domain.post.Post
import com.friendsDomain.friendsapp.infrastructure.builder.UserBuilder
import com.friendsDomain.friendsapp.infrastructure.builder.UserBuilder.Companion.aUser
import com.friendsDomain.friendsapp.timeline.state.TimelineState
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutorExtension::class)
class LoadPostsTest {

    @Test
    fun noPostsAvailable() {
        val viewModel = TimeLineViewModel()

        viewModel.timelineFor("ernestId")

        assertEquals(
            TimelineState.Posts(emptyList<Post>())
            ,viewModel.timelineState.value
        )
    }

    @Test
    fun postAvailable() {
        val tim = aUser().withId("timId").build()
        val timPosts = listOf(Post("postId",tim.id,"post text",1L))
        val viewModel = TimeLineViewModel()

        viewModel.timelineFor(tim.id)

        assertEquals(TimelineState.Posts(timPosts),
            viewModel.timelineState.value)
    }
    
    @Test
    fun postsFromFriends() {
        val anna = aUser().withId("annaId").build()
        val lucy = aUser().withId("lucyId").build()

        val viewModel = TimeLineViewModel()
        viewModel.timelineFor(anna.id)
        val lucyPosts = listOf(
            Post("post2", lucy.id, "post 2", 2L),
            Post("post1", lucy.id, "post 1",1L)
        )

    }
}