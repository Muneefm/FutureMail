package mnf.future.talk.Utils

fun String.isEmail() = this.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()