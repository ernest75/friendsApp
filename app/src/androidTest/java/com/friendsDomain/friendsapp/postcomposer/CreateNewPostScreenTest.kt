package com.friendsDomain.friendsapp.postcomposer

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.friendsDomain.friendsapp.MainActivity
import com.friendsDomain.friendsapp.domain.user.InMemoryUserData
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.koin.dsl.single


class CreateNewPostScreenTest {

    @get:Rule
    val createNewPostRule = createAndroidComposeRule<MainActivity>()

    @Test
    @Ignore("Under construction")
    fun createNewPost() {
        replaceUserDataWith(InMemoryUserData("ernestId"))
        launchPostComposerFor("ernest@eamil.com",createNewPostRule) {
            typePost("My new post")
            submit()
        } verify {
            newlyCreatedPostIsShown("ernestId", "30-10-2021 15:30", "My new post")
        }
    }

    private fun replaceUserDataWith(inMemoryUserData: InMemoryUserData) {
        val module = module {
            single { inMemoryUserData}
        }
        loadKoinModules(module)
    }
}

