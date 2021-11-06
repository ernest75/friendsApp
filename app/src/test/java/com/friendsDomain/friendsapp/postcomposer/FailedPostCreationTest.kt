package com.friendsDomain.friendsapp.postcomposer

import com.friendsDomain.friendsapp.InstantTaskExecutorExtension
import com.friendsDomain.friendsapp.domain.exceptions.BackendException
import com.friendsDomain.friendsapp.domain.exceptions.ConnectionUnavailableException
import com.friendsDomain.friendsapp.domain.post.Post
import com.friendsDomain.friendsapp.domain.post.PostCatalog
import com.friendsDomain.friendsapp.domain.post.PostRepository
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
            )
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
            )
        )

        viewModel.createPost(":offline:")

        Assertions.assertEquals(CreatePostState.Offline, viewModel.postState.value)
    }

    private class OfflinePostCatalog : PostCatalog {
        override fun addPost(userId: String, postText: String): Post {
            throw ConnectionUnavailableException()
        }

        override suspend fun postsFor(userIds: List<String>): List<Post> {
            TODO("Not yet implemented")
        }

    }

    private class UnavailablePostCatalog : PostCatalog {
        override fun addPost(userId: String, postText: String): Post {
            throw BackendException()
        }

        override suspend fun postsFor(userIds: List<String>): List<Post> {
            TODO("Not yet implemented")
        }

    }


}