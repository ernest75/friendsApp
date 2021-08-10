package com.friendsDomain.friendsapp.ui.signup

import androidx.annotation.StringRes
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.friendsDomain.friendsapp.R
import com.friendsDomain.friendsapp.ui.signup.state.SignUpScreenState
import com.friendsDomain.friendsapp.ui.signup.state.SignUpState

@Composable
fun SignUpScreen(
    signUpViewModel: SignUpViewModel,
    onSignedUp: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val screenState by remember {
        mutableStateOf(SignUpScreenState(coroutineScope))
    }
    val signUpState by signUpViewModel.signUpState.observeAsState()

    when (signUpState) {
        is SignUpState.SignedUp -> onSignedUp()
        is SignUpState.BadEmail -> screenState.isBadEmail = true
        is SignUpState.BadPassword -> screenState.isBadPassword = true
        is SignUpState.DuplicateAccount -> screenState.showToggleInfoMessage(R.string.duplicateAccountError)
        is SignUpState.BackEndError -> screenState.showToggleInfoMessage(R.string.createAccountError)
        is SignUpState.Offline -> screenState.showToggleInfoMessage(R.string.offLineError)
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            ScreenTitle(R.string.createAccount)
            Spacer(modifier = Modifier.height(16.dp))

            EmailField(
                value = screenState.email,
                isError = screenState.isBadEmail,
                onValueChange = { screenState.email = it })
            PasswordField(
                value = screenState.password,
                isError = screenState.isBadPassword,
                onValueChange = { screenState.password = it })
            AboutField(
                value = screenState.about,
                onValueChange = { screenState.about = it }
            )
            Spacer(modifier = Modifier.height(8.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    screenState.resetUiState()
                    with(screenState){
                        signUpViewModel.createAccount(email, password, about)
                    }
                },
            ) {
                Text(text = stringResource(id = R.string.signUp))
            }
        }
        InfoMessage(
            isVisible = screenState.isInfoMessageShowing,
            stringResource = screenState.currentInfoMessage
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun InfoMessage(
    isVisible: Boolean,
    @StringRes stringResource: Int
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(
            initialOffsetY = { fullHeight -> -fullHeight  },
            animationSpec = tween(durationMillis = 150, easing = FastOutLinearInEasing)
        ),
        exit = fadeOut(
            targetAlpha = 0f,
            animationSpec = tween(durationMillis = 250, easing = LinearOutSlowInEasing )
        )
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color= MaterialTheme.colors.error,
            elevation = 4.dp
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {

            }
            Text(
                text = stringResource(id = stringResource),
                modifier = Modifier.padding(16.dp),
                color = MaterialTheme.colors.onError
            )
        }
    }
}

@Composable
private fun ScreenTitle(@StringRes resource: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(resource),
            style = typography.h4
        )
    }
}

@Composable
private fun EmailField(
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        isError = isError,
        label = {
            val resource = if (isError) R.string.badEmailError else R.string.email
            Text(text = stringResource(id =resource))
        },
        onValueChange = onValueChange
    )
}

@Composable
private fun PasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean
) {
    var isVisible by remember { mutableStateOf(false) }
    val visualTransformation =
        if (isVisible) VisualTransformation.None else PasswordVisualTransformation()
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        isError = isError,
        trailingIcon = {
            VisibilityToggle(isVisible) {
                isVisible = !isVisible
            }
        },
        visualTransformation = visualTransformation,
        label = {
            val resource = if (isError) R.string.badPasswordError else R.string.password
            Text(text = stringResource(id = resource))
        },
        onValueChange = onValueChange
    )
}

@Composable
private fun VisibilityToggle(
    isVisible: Boolean,
    onToggle: () -> Unit
) {
    IconButton(onClick = {
        onToggle()
    }) {
        val resource = if (isVisible) R.drawable.ic_invisible else R.drawable.ic_visible
        Icon(
            painter = painterResource(id = resource),
            contentDescription = stringResource(id = R.string.toogleVisibility)
        )
    }
}

@Composable
fun AboutField(
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        label = {
            Text(text = stringResource(id = R.string.about))
        },
        onValueChange = onValueChange
    )
}
