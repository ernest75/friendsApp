package com.friendsDomain.friendsapp.ui.timeline

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.friendsDomain.friendsapp.R
import com.friendsDomain.friendsapp.domain.post.Post
import com.friendsDomain.friendsapp.timeline.TimelineViewModel
import com.friendsDomain.friendsapp.timeline.state.TimelineScreenState
import com.friendsDomain.friendsapp.timeline.state.TimelineState.*
import com.friendsDomain.friendsapp.ui.composables.BlockingLoading
import com.friendsDomain.friendsapp.ui.composables.InfoMessage
import com.friendsDomain.friendsapp.ui.composables.ScreenTitle

@Composable
fun TimelineScreen(
    userId: String,
    timelineViewModel: TimelineViewModel,
    onCreateNewPost: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val screenState by remember { mutableStateOf(TimelineScreenState(coroutineScope)) }
    val timelineState by timelineViewModel.timelineState.observeAsState()
    if (screenState.shouldLoadPostFor(userId)){
        timelineViewModel.timelineFor(userId)
    }

    when (timelineState) {
        is Loading -> screenState.showLoading()
        is Posts -> {
            val posts = (timelineState as Posts).posts
            screenState.updatePosts(posts)
        }
        is BackendError ->
            screenState.showInfoMessageError(R.string.fetchingTimelineError)
        is OfflineError ->
            screenState.showInfoMessageError(R.string.offLineError)
    }

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            ScreenTitle(resource = R.string.timeline)
            Spacer(modifier = Modifier.height(16.dp))
            Box(modifier = Modifier.fillMaxSize()) {
                PostsList(
                    posts = screenState.posts,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
                FloatingActionButton(
                    onClick = { onCreateNewPost() },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .testTag(stringResource(id = R.string.createNewPost))

                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(id = R.string.createNewPost)
                    )
                }
            }
        }
        InfoMessage(isVisible = screenState.isInfoMessageShowing,
            stringResource = screenState.currentInfoMessage )
        BlockingLoading(isShowing = screenState.isLoading)
    }
}

@Composable
private fun PostsList(
    posts: List<Post>,
    modifier: Modifier
) {
    if (posts.isEmpty()) {
        Text(
            text = stringResource(id = R.string.emptyTimelineMessage),
            modifier = modifier
        )
    } else {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(posts) { post ->
                PostItem(post)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun PostItem(
    post: Post,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(16.dp))
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.onSurface,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Text(
            text = post.postText,
            modifier = Modifier.padding(16.dp)
        )
    }
}

//@Preview
//@Composable
//private fun PostsListPreview(){
//    val posts = (0..100).map { index ->
//        Post("$index", "user$index", "This is a post number $index", index.toLong())
//    }
//    PostsList(posts = posts)
//}
