package com.friendsDomain.friendsapp.timeline

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.friendsDomain.friendsapp.MainActivity
import com.friendsDomain.friendsapp.R
import com.friendsDomain.friendsapp.domain.post.Post
import com.friendsDomain.friendsapp.signup.launchSignUpScreen

typealias MainActivityRule = AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>


public fun launchTimelineFor(
    email: String,
    password: String,
    timelineTestRule: MainActivityRule,
    block: TimelineRobot.() -> Unit): TimelineRobot {
    launchSignUpScreen(timelineTestRule){
        typeEmail(email)
        typePassword(password)
        submit()
    }
    return TimelineRobot(timelineTestRule).apply(block)
}

class TimelineRobot(
    private val rule: MainActivityRule
    ) {

    fun tapOnCreateNewPost() {
        val createNewPost = rule.activity.getString(R.string.createNewPost)
        rule.onNodeWithTag(createNewPost)
            .performClick()
    }

    infix fun verify(block: TimelineVerificationRobot.() -> Unit
    ):TimelineVerificationRobot {
        return TimelineVerificationRobot(rule).apply(block)
    }

}

class TimelineVerificationRobot(
    private val rule: MainActivityRule
    ) {
    fun emptyTimelineMessageIsDisplayed() {
        val emptyTimelineMessage = rule.activity.getString(R.string.emptyTimelineMessage)
        rule.onNodeWithText(emptyTimelineMessage).assertIsDisplayed()
    }

    fun postsAreDisplayed(vararg posts: Post) {
        posts.forEach { post ->
            rule.onNodeWithText(post.postText)
                .assertIsDisplayed()
        }
    }

    fun newPostComposerIsDisplayed() {
        val createNewPost = rule.activity.getString(R.string.createNewPost)
        rule.onNodeWithText(createNewPost).assertIsDisplayed()
    }

    fun loadingIndicatorIsDisplayed() {
        val loading = rule.activity.getString(R.string.loading)
        rule.onNodeWithTag(loading)
            .assertIsDisplayed()
    }

    fun backendErrorIsDisplayed() {
        val errorMessage = rule.activity
            .getString(R.string.fetchingTimelineError)
        rule.onNodeWithText(errorMessage)
            .assertIsDisplayed()
    }

    fun offlineErrorIsDisplayed() {
        val errorMessage = rule.activity
            .getString(R.string.offLineError)
        rule.onNodeWithText(errorMessage)
            .assertIsDisplayed()
    }
}
