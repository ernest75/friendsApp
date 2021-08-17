package com.friendsDomain.friendsapp.timeline

import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.friendsDomain.friendsapp.MainActivity
import com.friendsDomain.friendsapp.domain.post.InMemoryPostCatalog
import com.friendsDomain.friendsapp.domain.post.Post
import com.friendsDomain.friendsapp.domain.post.PostCatalog
import com.friendsDomain.friendsapp.timeline.state.TimelineState
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module


class TimelineScreenTest {

    @get:Rule
    val timelineTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun showEmptyTimelineMessage(){
        val email = "lucy@friends.com"
        val password = "123Aerb$%&&ddd"
        launchTimelineFor(email,password,timelineTestRule){

        } verify{
            emptyTimelineMessageIsDisplayed()
        }
    }

    @Test
    fun showsAvailablePosts(){
        val email = "bob@friends.com"
        val password = "b0bPassword###2021"
        val post1 = Post("post1","bobId","Bob's first post",1L)
        val post2 = Post("post2","bobId","Bob's second post",2L)

        val postCatalog = InMemoryPostCatalog(listOf(post1, post2))
        val replaceModule = module {
            factory<PostCatalog> { postCatalog }
        }
        loadKoinModules(replaceModule)

        launchTimelineFor(email, password, timelineTestRule){

        }verify{
            postsAreDisplayed(post1,post2)
        }
    }
}
