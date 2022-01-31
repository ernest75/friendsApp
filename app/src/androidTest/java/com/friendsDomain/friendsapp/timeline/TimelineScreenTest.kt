package com.friendsDomain.friendsapp.timeline

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.friendsDomain.friendsapp.MainActivity
import com.friendsDomain.friendsapp.domain.post.*
import kotlinx.coroutines.delay
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module


class TimelineScreenTest {

    @get:Rule
    val timelineTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun showEmptyTimelineMessage() {
        val email = "lucy@friends.com"
        val password = "123Aerb$%&&ddd"
        launchTimelineFor(email, password, timelineTestRule) {
            //no operation
        } verify {
            emptyTimelineMessageIsDisplayed()
        }
    }

    @Test
    fun showsAvailablePosts() {
        val email = "bob@friends.com"
        val password = "b0bPassword###2021"
        val post1 = Post("post1", "bobId", "Bob's first post", 1L)
        val post2 = Post("post2", "bobId", "Bob's second post", 2L)

        replacePostCatalogWith(InMemoryPostCatalog(listOf(post1, post2)))

        launchTimelineFor(email, password, timelineTestRule) {

        } verify {
            postsAreDisplayed(post1, post2)
        }
    }

    @Test
    fun opensPostComposer() {
        launchTimelineFor("test@friends.com", "Passw0rd!!", timelineTestRule) {
            tapOnCreateNewPost()
        } verify {
            newPostComposerIsDisplayed()
        }
    }

    @Test
    fun showLoadingIndicator() {
        replacePostCatalogWith(DelayingPostCatalog())
        launchTimelineFor(
            "testloading@friends.com",
            "Passw0rd!!", timelineTestRule
        ) {
            //no operation
        } verify {
            loadingIndicatorIsDisplayed()
        }
    }

    @Test
    fun showBackendError() {
        replacePostCatalogWith(UnavailablePostCatalog())
        launchTimelineFor(
            "testbackEndError@friends.com",
            "Passw0rd!!", timelineTestRule
        ) {
            //no operation
        } verify {
            backendErrorIsDisplayed()
        }
    }

    @Test
    fun showOfflineError() {
        replacePostCatalogWith(OfflinePostCatalog())
        launchTimelineFor(
            "offlineError@friends.com",
            "Passw0rd!!", timelineTestRule
        ){
            //no operation
        } verify {
            offlineErrorIsDisplayed()
        }
    }

    @After
    fun tearDown() {
        replacePostCatalogWith(InMemoryPostCatalog())
    }

    private fun replacePostCatalogWith(postCatalog: PostCatalog) {
        val replaceModule = module {
            factory<PostCatalog> { postCatalog }
        }
        loadKoinModules(replaceModule)
    }

    class DelayingPostCatalog : PostCatalog {
        override fun addPost(userId: String, postText: String): Post {
            TODO("Not yet implemented")
        }

        override suspend fun postsFor(userIds: List<String>): List<Post> {
            delay(2000)
            return emptyList()
        }
    }
}
