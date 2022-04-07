package dev.chintansoni.expensetracker.ui.util

/**
 * Checks if email is invalid
 */
fun String.isInValidEmail(): Boolean =
    !android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()

/**
 * Checks if email string is valid or not.
 * If valid, then returns empty string
 * Else returns error string
 */
fun String.validateEmail(): String = if (this.isEmpty())
    "Enter your email"
else if (this.isInValidEmail())
    "Enter a valid email"
else ""

/**
 * Checks if password string is valid or not.
 * If valid, then returns empty string
 * Else returns error string
 */
fun String.validatePassword(): String = if (this.isEmpty()) "Enter your Password" else ""