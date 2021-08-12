package com.friendsDomain.friendsapp.app

import kotlinx.coroutines.Dispatchers

class TestDispatchers : CoroutineDispatchers {
    override val background = Dispatchers.Unconfined
}