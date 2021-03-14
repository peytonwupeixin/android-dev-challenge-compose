
package com.example.androiddevchallenge.log_in

class PasswordState :
    TextFieldState(validator = ::isPasswordValid, errorFor = ::passwordValidationError)

private fun isPasswordValid(password: String): Boolean {
    return password.length > 8
}

@Suppress("UNUSED_PARAMETER")
private fun passwordValidationError(password: String): String {
    return "Invalid password"
}
