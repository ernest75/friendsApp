package com.friendsDomain.friendsapp.ui.timeline

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.friendsDomain.friendsapp.R
import com.friendsDomain.friendsapp.domain.post.Post
import com.friendsDomain.friendsapp.timeline.TimelineViewModel
import com.friendsDomain.friendsapp.timeline.state.TimelineState
import com.friendsDomain.friendsapp.timeline.state.TimelineState.*
import com.friendsDomain.friendsapp.ui.composables.ScreenTitle

class TimelineScreenState{
    var posts by mutableStateOf(emptyList<Post>())

    fun updatePosts(newPosts: List<Post>) {
        this.posts = newPosts
    }

}

@Composable
fun TimelineScreen(
    userId: String,
    timelineViewModel: TimelineViewModel
) {
    val screenState by remember { mutableStateOf(TimelineScreenState())}
    val timelineState by timelineViewModel.timelineState.observeAsState()
    timelineViewModel.timelineFor(userId)

    if (timelineState is Posts){
        val posts = (timelineState as Posts).posts
        screenState.updatePosts(posts)
    }
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            ){
        ScreenTitle(resource = R.string.timeline)
        PostsList(screenState.posts)
    }
}

@Composable
private fun PostsList(posts: List<Post>) {
    Text(text = stringResource(id = R.string.emptyTimelineMessage))
}
