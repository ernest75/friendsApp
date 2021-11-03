package com.friendsDomain.friendsapp.infrastructure

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.regex.Pattern

class UUIDGeneratorTest {

    private companion object {
        private const val REGEX_PATTERN = """[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}"""
    }
    
    @Test
    fun generatesCorrectUUID() {
        val uuid = UUIDGenerator().next()
        val pattern = Pattern.compile(REGEX_PATTERN)
        Assertions.assertTrue(pattern.matcher(uuid).matches())
    }
}