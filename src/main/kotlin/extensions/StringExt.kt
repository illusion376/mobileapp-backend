package extensions

object EmailValidator {
    val EMAIL_REGEX = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}$")
}

fun String.isValidEmail(): Boolean {
    return this.matches(EmailValidator.EMAIL_REGEX)
}