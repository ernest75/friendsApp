package com.friendsDomain.friendsapp.infrastructure

import java.util.*

class UUIDGenerator: IdGenerator {

    override fun next(): String {
        return UUID.randomUUID().toString()
    }

}
