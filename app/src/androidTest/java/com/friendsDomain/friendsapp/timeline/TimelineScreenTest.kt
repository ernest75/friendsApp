package com.friendsDomain.friendsapp.timeline

import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.friendsDomain.friendsapp.MainActivity
import org.junit.Rule
import org.junit.Test


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
}
