package com.friendsDomain.friendsapp.postcomposer

import com.friendsDomain.friendsapp.InstantTaskExecutorExtension
import com.friendsDomain.friendsapp.app.TestDispatchers
import com.friendsDomain.friendsapp.domain.post.InMemoryPostCatalog
import com.friendsDomain.friendsapp.domain.post.Post
import com.friendsDomain.friendsapp.domain.post.PostRepository
import com.friendsDomain.friendsapp.domain.user.InMemoryUserData
import com.friendsDomain.friendsapp.infrastructure.ControllableClock
import com.friendsDomain.friendsapp.infrastructure.ControllableIdGenerator
import com.friendsDomain.friendsapp.postcomposer.state.CreatePostState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutorExtension::class)
class CreateAPostTest {

    @Test
    fun aPostIsCreated() {
        val postText = "First post"
        val post = Post("postId", "userId", postText, 1L)
        val userData = InMemoryUserData("userId")
        val clock = ControllableClock(1L)
        val idGenerator = ControllableIdGenerator("postId")
        val viewModel = CreatePostViewModel(
            PostRepository(
                userData, InMemoryPostCatalog(
                    idGenerator = idGenerator,
                    clock = clock
                )
            ),
            dispatchers = TestDispatchers()
        )

        viewModel.createPost(postText)

        assertEquals(CreatePostState.Created(post), viewModel.postState.value)
    }

    @Test
    fun anotherPostCreated() {
        val postText = "Second post"
        val post = Post("postId2", "userId", postText, 2L)
        val userData = InMemoryUserData("userId")
        val clock = ControllableClock(2L)
        val idGenerator = ControllableIdGenerator("postId2")
        val viewModel = CreatePostViewModel(
            PostRepository(
                userData, InMemoryPostCatalog(
                    idGenerator = idGenerator,
                    clock = clock
                )
            ),
            dispatchers = TestDispatchers()
        )

        viewModel.createPost(postText)

        assertEquals(CreatePostState.Created(post), viewModel.postState.value)
    }
}