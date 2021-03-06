package com.friendsDomain.friendsapp.app

import android.service.autofill.UserData
import com.friendsDomain.friendsapp.domain.post.InMemoryPostCatalog
import com.friendsDomain.friendsapp.domain.post.PostCatalog
import com.friendsDomain.friendsapp.domain.post.PostRepository
import com.friendsDomain.friendsapp.domain.timeline.TimelineRepository
import com.friendsDomain.friendsapp.domain.user.InMemoryUserCatalog
import com.friendsDomain.friendsapp.domain.user.InMemoryUserData
import com.friendsDomain.friendsapp.domain.user.UserCatalog
import com.friendsDomain.friendsapp.domain.user.UserRepository
import com.friendsDomain.friendsapp.domain.validation.RegexCredentialsValidator
import com.friendsDomain.friendsapp.postcomposer.CreatePostViewModel
import com.friendsDomain.friendsapp.timeline.TimelineViewModel
import com.friendsDomain.friendsapp.ui.signup.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val applicationModule = module{
    single<CoroutineDispatchers> {DefaultDispatchers()}
    single<UserCatalog> { InMemoryUserCatalog() }
    single<PostCatalog> { InMemoryPostCatalog() }
    single { InMemoryUserData("")}
    factory { RegexCredentialsValidator()}
    factory { UserRepository(userCatalog = get()) }
    factory { TimelineRepository(userCatalog = get(),postCatalog = get()) }
    factory { PostRepository(userData = get(), inMemoryPostCatalog = get()) }

    viewModel {
        SignUpViewModel(
            credentialsValidator = get(),
            userRepository = get(),
            dispatchers = get()
        )
    }

    viewModel {
        TimelineViewModel(timelineRepository = get(), dispatchers = get())
    }

    viewModel {
        CreatePostViewModel(postRepository = get(), dispatchers = get())
    }
}