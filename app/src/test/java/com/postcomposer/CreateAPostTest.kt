package com.postcomposer

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
        val viewModel = CreatePostViewModel()

        val postText = "First post"
        viewModel.createPost(postText)
        val post = Post("postId","userId","postText",1L)
        assertEquals(CreatePostState.Created(post),viewModel.posState.value)
    }
}