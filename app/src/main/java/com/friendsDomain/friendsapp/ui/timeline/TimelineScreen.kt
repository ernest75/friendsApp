package com.friendsDomain.friendsapp.ui.timeline

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.friendsDomain.friendsapp.R
import com.friendsDomain.friendsapp.timeline.TimelineViewModel

@Composable
fun TimelineScreen(
    userId: String,
    timelineViewModel: TimelineViewModel
) {
    Column {
        Text(text = stringResource(id = R.string.timeline))
        Text(text = stringResource(id = R.string.emptyTimelineMessage))
    }
    timelineViewModel.timelineFor(userId)
}