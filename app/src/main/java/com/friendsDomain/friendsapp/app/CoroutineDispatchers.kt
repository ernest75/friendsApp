package com.friendsDomain.friendsapp.app

import kotlinx.coroutines.CoroutineDispatcher

interface CoroutineDispatchers {
    val background: CoroutineDispatcher
}