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
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutorExtension::class)
class RenderingCreatePostStatesTest {
    private val userID = "userId"
    private val postId = "postId"
    private val postText = "postText"
    private val timestamp = 1L


    private val clock = ControllableClock(1L)
    private val idGenerator = ControllableIdGenerator(postId)
    private val postCatalog = InMemoryPostCatalog(idGenerator = idGenerator, clock = clock)
    private val userData = InMemoryUserData(userID)
    private val postRepository = PostRepository(userData, postCatalog)
    private val dispatchers = TestDispatchers()
    val viewModel = CreatePostViewModel(postRepository, dispatchers)

    @Test
    fun uiStatesAreDeliveredInParticularOrder() {
        val post = Post(postId, userID, postText, timestamp)
        val deliveredStates = mutableListOf<CreatePostState>()
        viewModel.postState.observeForever { deliveredStates.add(it) }

        viewModel.createPost(postText)

        Assertions.assertEquals(
            listOf(CreatePostState.Loading, CreatePostState.Created(post)),
            deliveredStates
        )
    }
}