package com.friendsDomain.friendsapp.timeline

import com.friendsDomain.friendsapp.InstantTaskExecutorExtension
import com.friendsDomain.friendsapp.domain.post.InMemoryPostCatalog
import com.friendsDomain.friendsapp.domain.post.Post
import com.friendsDomain.friendsapp.domain.user.Following
import com.friendsDomain.friendsapp.domain.user.InMemoryUserCatalog
import com.friendsDomain.friendsapp.infrastructure.builder.UserBuilder.Companion.aUser
import com.friendsDomain.friendsapp.timeline.state.TimelineState
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutorExtension::class)
class LoadPostsTest {

    private val lucy = aUser().withId("lucyId").build()
    private val sara = aUser().withId("saraId").build()
    private val anna = aUser().withId("annaId").build()
    private val tim = aUser().withId("timId").build()

    private val timPosts = listOf(
        Post("postId", tim.id, "post text", 1L)
    )
    private val lucyPosts = listOf(
        Post("post2", lucy.id, "post 2", 2L),
        Post("post1", lucy.id, "post 1", 1L)
    )
    private val saraPosts = listOf(
        Post("post4", sara.id, "post 4", 4L),
        Post("post3", sara.id, "post 3", 3L)
    )

    private val availablePosts = timPosts + lucyPosts + saraPosts

    @Test
    fun noPostsAvailable() {
        val viewModel = TimeLineViewModel(
            InMemoryUserCatalog(), InMemoryPostCatalog(availablePosts)
        )

        viewModel.timelineFor("ernestId")

        assertEquals(
            TimelineState.Posts(emptyList<Post>()), viewModel.timelineState.value
        )
    }

    @Test
    fun postAvailable() {
        val viewModel = TimeLineViewModel(
            InMemoryUserCatalog(),
            InMemoryPostCatalog(availablePosts)
        )

        viewModel.timelineFor(tim.id)

        assertEquals(
            TimelineState.Posts(timPosts),
            viewModel.timelineState.value
        )
    }

    @Test
    fun postsFromFriends() {
        val viewModel = TimeLineViewModel(
            InMemoryUserCatalog(
                followings = listOf(
                    Following(anna.id, lucy.id)
                )
            ),
            InMemoryPostCatalog(availablePosts)
        )
        viewModel.timelineFor(anna.id)

        assertEquals(
            TimelineState.Posts(lucyPosts),
            viewModel.timelineState.value
        )
    }

    @Test
    fun postFromFriendsAlongOwn() {
        val viewModel = TimeLineViewModel(
            InMemoryUserCatalog(
                followings = listOf(
                    Following(sara.id, lucy.id)
                )
            ),
            InMemoryPostCatalog(availablePosts)
        )

        viewModel.timelineFor(sara.id)

        assertEquals(
            TimelineState.Posts(lucyPosts + saraPosts),
            viewModel.timelineState.value
        )
    }
}