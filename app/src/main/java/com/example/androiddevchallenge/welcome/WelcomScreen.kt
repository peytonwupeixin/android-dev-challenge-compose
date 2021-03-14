package com.example.androiddevchallenge.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.R.string
import com.example.androiddevchallenge.ui.theme.LocalCustomerColor
import com.example.androiddevchallenge.ui.theme.LocalElevations
import com.example.androiddevchallenge.ui.theme.LocalImages
import kotlinx.coroutines.launch

@Composable
fun WelcomeScreen(onLoginClick: () -> Unit) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val snackbarErrorText = stringResource(id = R.string.feature_not_available)
    val snackbarActionLabel = stringResource(id = R.string.dismiss)

    Surface(color = MaterialTheme.colors.primary) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Image(
                painter = painterResource(id = LocalImages.current.welcomeBg),
                contentDescription = null,
            )
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = LocalImages.current.welcomeIllos),
                    contentDescription = "welcome illos",
                    modifier = Modifier
                        .offset(x = 88.dp)
                        .padding(top = 72.dp),
                )
                Image(
                    painter = painterResource(id = LocalImages.current.logo),
                    contentDescription = "logo",
                    modifier = Modifier.padding(top = 48.dp)
                )
                Text(
                    text = "Beautiful home garden solutions",
                    modifier = Modifier.paddingFromBaseline(top = 32.dp),
                    color = MaterialTheme.colors.onPrimary
                )
                Button(modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 40.dp)
                    .height(48.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary,
                    contentColor = MaterialTheme.colors.onSecondary),
                    shape = MaterialTheme.shapes.medium, onClick = {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = snackbarErrorText,
                                actionLabel = snackbarActionLabel
                            )
                        }
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.create_account),
                    )
                }
                TextButton(
                    colors  = ButtonDefaults.textButtonColors(contentColor = LocalCustomerColor.current.logIn,
                    disabledContentColor = LocalCustomerColor.current.logIn),
                    modifier = Modifier.paddingFromBaseline(top = 40.dp),onClick = onLoginClick){
                    Text(
                        text = stringResource(id = R.string.log_in),
                    )
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        ErrorSnackbar(
            snackbarHostState = snackbarHostState,
            onDismiss = { snackbarHostState.currentSnackbarData?.dismiss() },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}
@Composable
fun ErrorSnackbar(
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = { }
) {
    SnackbarHost(
        hostState = snackbarHostState,
        snackbar = { data ->
            Snackbar(
                modifier = Modifier.padding(16.dp),
                content = {
                    Text(
                        text = data.message,
                        style = MaterialTheme.typography.body2
                    )
                },
                action = {
                    data.actionLabel?.let {
                        TextButton(onClick = onDismiss) {
                            Text(
                                text = stringResource(id = string.dismiss),
                                color = MaterialTheme.colors.primary
                            )
                        }
                    }
                },
                elevation= LocalElevations.current.snackBar,
                shape = MaterialTheme.shapes.small
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(Alignment.Bottom)
    )
}