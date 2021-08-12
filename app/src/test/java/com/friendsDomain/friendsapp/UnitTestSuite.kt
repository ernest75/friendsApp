package com.friendsDomain.friendsapp

import com.friendsDomain.friendsapp.signup.CreateAnAccountTest
import com.friendsDomain.friendsapp.signup.CredentialsValidationTest
import com.friendsDomain.friendsapp.signup.FailedAccountCreationTest
import com.friendsDomain.friendsapp.signup.RenderingSignupStatesTest
import org.junit.platform.runner.JUnitPlatform
import org.junit.platform.suite.api.SelectClasses
import org.junit.platform.suite.api.SelectPackages
import org.junit.runner.Request.runner
import org.junit.runner.RunWith


@RunWith(JUnitPlatform::class)
@SelectClasses(
    CreateAnAccountTest::class,
    CredentialsValidationTest::class,
    FailedAccountCreationTest::class,
    RenderingSignupStatesTest::class
)
class UnitTestSuite {
}