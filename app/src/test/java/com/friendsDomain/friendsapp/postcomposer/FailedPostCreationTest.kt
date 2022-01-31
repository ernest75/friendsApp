package com.friendsDomain.friendsapp.postcomposer

import com.friendsDomain.friendsapp.InstantTaskExecutorExtension
import com.friendsDomain.friendsapp.app.TestDispatchers
import com.friendsDomain.friendsapp.domain.post.OfflinePostCatalog
import com.friendsDomain.friendsapp.domain.post.PostRepository
import com.friendsDomain.friendsapp.domain.post.UnavailablePostCatalog
import com.friendsDomain.friendsapp.domain.user.InMemoryUserData
import com.friendsDomain.friendsapp.postcomposer.state.CreatePostState
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutorExtension::class)
class FailedPostCreationTest {

    @Test
    fun backEndError() {
        val viewModel = CreatePostViewModel(
            PostRepository(
                InMemoryUserData("userId"),
                UnavailablePostCatalog()
            ),
            dispatchers = TestDispatchers()
        )

        viewModel.createPost(":backEnd:")

        Assertions.assertEquals(CreatePostState.BackEndError, viewModel.postState.value)
    }

    @Test
    fun offlineError() {
        val viewModel = CreatePostViewModel(
            PostRepository(
                InMemoryUserData("userId"),
                OfflinePostCatalog()
            ),
            dispatchers = TestDispatchers()
        )

        viewModel.createPost(":offline:")

        Assertions.assertEquals(CreatePostState.Offline, viewModel.postState.value)
    }
}