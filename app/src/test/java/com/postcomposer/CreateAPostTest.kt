package com.postcomposer

import androidx.lifecycle.viewmodel.compose.viewModel
import com.friendsDomain.friendsapp.InstantTaskExecutorExtension
import com.friendsDomain.friendsapp.domain.post.Post
import com.postcomposer.state.CreatePostState
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutorExtension::class)
class CreateAPostTest {
    
    @Test
    fun aPostIsCreated() {
        val postText = "First post"
        val post = Post("postId","userId",postText,1L)
        val viewModel = CreatePostViewModel()

        viewModel.createPost(postText)

        assertEquals(CreatePostState.Created(post),viewModel.posState.value)
    }

    @Test
    fun anotherPostCreated() {
        val postText = "Second post"
        val post = Post("postId2","userId", postText,2L)
        val viewModel = CreatePostViewModel()

        viewModel.createPost(postText)

        assertEquals(CreatePostState.Created(post), viewModel.posState.value)
    }
}