package com.friendsDomain.friendsapp.postcomposer

import com.friendsDomain.friendsapp.InstantTaskExecutorExtension
import com.friendsDomain.friendsapp.domain.post.PostRepository
import com.friendsDomain.friendsapp.domain.user.InMemoryUserData
import com.friendsDomain.friendsapp.infrastructure.ControllableClock
import com.friendsDomain.friendsapp.infrastructure.ControllableIdGenerator
import com.friendsDomain.friendsapp.postcomposer.state.CreatePostState
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutorExtension::class)
class FailedPostCreationTest {

    @Test
    fun backEndError() {
        val userData = InMemoryUserData("userId")
        val clock = ControllableClock(1L)
        val idGenerator = ControllableIdGenerator("postId1")
        val viewModel = CreatePostViewModel(
            PostRepository(userData, clock, idGenerator)
        )

        viewModel.createPost(":backEnd:")

        Assertions.assertEquals(CreatePostState.BackEndError, viewModel.postState.value)
    }

    @Test
    fun offlineError() {
        val userData = InMemoryUserData("userId")
        val clock = ControllableClock(1L)
        val idGenerator = ControllableIdGenerator("postId2")
        val viewModel = CreatePostViewModel(
            PostRepository(userData, clock, idGenerator)
        )

        viewModel.createPost(":offline:")

        Assertions.assertEquals(CreatePostState.Offline, viewModel.postState.value)
    }


}