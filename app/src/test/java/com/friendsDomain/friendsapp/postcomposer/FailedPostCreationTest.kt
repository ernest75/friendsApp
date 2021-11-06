package com.friendsDomain.friendsapp.postcomposer

import com.friendsDomain.friendsapp.InstantTaskExecutorExtension
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
        val viewModel = CreatePostViewModel(
            InMemoryUserData("userId"),
            ControllableClock(1L),
            ControllableIdGenerator("postId1")
        )

        viewModel.createPost(":backEnd:")

        Assertions.assertEquals(CreatePostState.BackEndError, viewModel.postState.value)
    }

    @Test
    fun offlineError() {
        val viewModel = CreatePostViewModel(
            InMemoryUserData("userId"),
            ControllableClock(1L),
            ControllableIdGenerator("postId2")
        )

        viewModel.createPost(":offline:")

        Assertions.assertEquals(CreatePostState.Offline, viewModel.postState.value)
    }


}