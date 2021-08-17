package com.friendsDomain.friendsapp.domain.timeline

import com.friendsDomain.friendsapp.domain.exceptions.BackendException
import com.friendsDomain.friendsapp.domain.exceptions.ConnectionUnavailableException
import com.friendsDomain.friendsapp.domain.post.PostCatalog
import com.friendsDomain.friendsapp.domain.user.UserCatalog
import com.friendsDomain.friendsapp.timeline.state.TimelineState

class TimelineRepository(
    private val userCatalog: UserCatalog,
    private val postCatalog: PostCatalog
) {
     fun getTimelineFor(userId: String): TimelineState {
        return try {
            val userIds = listOf(userId) + userCatalog.followedBy(userId)
            val postsForUser = postCatalog.postsFor(userIds)
            TimelineState.Posts(postsForUser)
        } catch (backendException: BackendException) {
            TimelineState.BackendError
        } catch (noConnectionException: ConnectionUnavailableException) {
            TimelineState.OfflineError
        }
    }
}