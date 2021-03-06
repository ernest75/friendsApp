package com.friendsDomain.friendsapp.postcomposer

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.friendsDomain.friendsapp.MainActivity
import com.friendsDomain.friendsapp.R
import com.friendsDomain.friendsapp.timeline.launchTimelineFor

private typealias MainActivityRule = AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>

fun launchPostComposerFor(
    email: String,
    createNewPostRule: MainActivityRule,
    block: CreateNewPostRobot.() -> Unit
): CreateNewPostRobot {
    launchTimelineFor(email, "iRRelevantPa$1w0rd!", createNewPostRule) {
        tapOnCreateNewPost()
    }
    return CreateNewPostRobot(createNewPostRule).apply(block)
}

class CreateNewPostRobot(private val rule: MainActivityRule) {
    fun typePost(postContent: String) {
        val newPostHint = rule.activity.getString(R.string.newPostHint)
        rule.onNodeWithText(newPostHint)
            .performTextInput(postContent)
    }

    fun submit() {
        val submitPost = rule.activity.getString(R.string.submitPost)
        rule.onNodeWithTag(submitPost)
            .performClick()
    }

    infix fun verify(
        block: CreateNewPostVerificationRobot.() -> Unit
    ): CreateNewPostVerificationRobot {
        return CreateNewPostVerificationRobot(rule).apply(block)
    }
}

class CreateNewPostVerificationRobot(private val rule: MainActivityRule) {

    fun newlyCreatedPostIsShown(
        userId: String,
        dateTime: String,
        postContent: String
    ) {
        rule.onNodeWithText(userId).assertIsDisplayed()
        rule.onNodeWithText(dateTime).assertIsDisplayed()
        rule.onNodeWithText(postContent).assertIsDisplayed()
    }

}
