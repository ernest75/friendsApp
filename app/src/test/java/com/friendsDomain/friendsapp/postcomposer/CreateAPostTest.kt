package com.friendsDomain.friendsapp.postcomposer

import com.friendsDomain.friendsapp.InstantTaskExecutorExtension
import com.friendsDomain.friendsapp.domain.post.Post
import com.friendsDomain.friendsapp.domain.user.InMemoryUserData
import com.friendsDomain.friendsapp.infrastructure.ControllableClock
import com.friendsDomain.friendsapp.postcomposer.state.CreatePostState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutorExtension::class)
class CreateAPostTest {

    private val userId = "userId"
    private val userData = InMemoryUserData(userId)

    @Test
    fun aPostIsCreated() {
        val postText = "First post"
        val post = Post("postId","userId",postText,1L)
        val viewModel = CreatePostViewModel(userData, ControllableClock(1L))

        viewModel.createPost(postText)

        assertEquals(CreatePostState.Created(post),viewModel.posState.value)
    }

    @Test
    fun anotherPostCreated() {
        val postText = "Second post"
        val post = Post("postId2", userId, postText,2L)
        val viewModel = CreatePostViewModel(userData, ControllableClock(2L))

        viewModel.createPost(postText)

        assertEquals(CreatePostState.Created(post), viewModel.posState.value)
    }
}