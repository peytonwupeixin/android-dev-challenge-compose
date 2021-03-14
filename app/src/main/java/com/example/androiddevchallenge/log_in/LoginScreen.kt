package com.example.androiddevchallenge.log_in

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.R.string

@Composable
fun LoginScreen(gotoHome: (userName: String, password: String) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Log in with email",
            modifier = Modifier.paddingFromBaseline(top = 184.dp, bottom = 16.dp),
            style = MaterialTheme.typography.h1,
            color = MaterialTheme.colors.onPrimary
        )
        SignInContent(
            onSignInSubmitted = gotoHome
        )
    }
}

@Composable
fun SignInContent(
    onSignInSubmitted: (email: String, password: String) -> Unit,
) {
    val indexStyle = MaterialTheme.typography.body2.copy(
        textDecoration = TextDecoration.Underline,
    ).toSpanStyle()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        val focusRequester = remember { FocusRequester() }
        val emailState = remember { EmailState() }
        Email(emailState, onImeAction = { focusRequester.requestFocus() })
        val passwordState = remember { PasswordState() }
        Password(
            label = stringResource(id = R.string.password),
            passwordState = passwordState,
            modifier = Modifier.focusRequester(focusRequester),
            onImeAction = {
                if (emailState.isValid && passwordState.isValid){
                    onSignInSubmitted(emailState.text, passwordState.text)
                }
            }
        )
        Text(
            modifier = Modifier.paddingFromBaseline(top = 24.dp, bottom = 16.dp),
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onPrimary,
            textAlign = TextAlign.Center,
            text = buildAnnotatedString {
                append("By clicking below,you agree to our ")
                withStyle(style = indexStyle) {
                    append("Terms of Use")
                }
                append(" and consent to our ")
                withStyle(style = indexStyle) {
                    append("Privacy Policy")
                }
                append(".")
            })
        Button(
            onClick = { onSignInSubmitted(emailState.text, passwordState.text) },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.secondary,
                contentColor = MaterialTheme.colors.onSecondary
            ),
            shape = MaterialTheme.shapes.medium,
            enabled = emailState.isValid && passwordState.isValid
        ) {
            Text(
                text = stringResource(id = string.log_in)
            )
        }
    }
}

@Composable
fun Email(
    emailState: TextFieldState = remember { EmailState() },
    imeAction: ImeAction = ImeAction.Next,
    onImeAction: () -> Unit = {}
) {
    OutlinedTextField(
        value = emailState.text,
        onValueChange = {
            emailState.text = it
            emailState.enableShowErrors()
        },
        label = {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text = stringResource(id = R.string.email),
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onPrimary
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                val focused = focusState == FocusState.Active
                emailState.onFocusChange(focused)
                if (!focused) {
                    emailState.enableShowErrors()
                }
            },
        textStyle = MaterialTheme.typography.body2,
        isError = emailState.showErrors(),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = imeAction),
        keyboardActions = KeyboardActions(
            onNext = {
                onImeAction()
            }
        )
    )
    emailState.getError()?.let { error -> TextFieldError(textError = error) }
}

@Composable
fun Password(
    label: String,
    passwordState: TextFieldState,
    modifier: Modifier = Modifier,
    imeAction: ImeAction = ImeAction.Done,
    onImeAction: () -> Unit = {}
) {
    OutlinedTextField(
        value = passwordState.text,
        onValueChange = {
            passwordState.text = it
            passwordState.enableShowErrors()
        },
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                val focused = focusState == FocusState.Active
                passwordState.onFocusChange(focused)
                if (!focused) {
                    passwordState.enableShowErrors()
                }
            },
        textStyle = MaterialTheme.typography.body2,
        label = {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onPrimary
                )
            }
        },
        visualTransformation = PasswordVisualTransformation(),
        isError = passwordState.showErrors(),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = imeAction),
        keyboardActions = KeyboardActions(
            onDone = {
                onImeAction()
            }
        )
    )
    passwordState.getError()?.let { error -> TextFieldError(textError = error) }
}

/**
 * To be removed when [TextField]s support error
 */
@Composable
fun TextFieldError(textError: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = textError,
            modifier = Modifier.fillMaxWidth(),
            style = LocalTextStyle.current.copy(color = MaterialTheme.colors.error)
        )
    }
}