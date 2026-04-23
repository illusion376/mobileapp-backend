import features.register.configureRegisterRouter
import features.login.configureLoginRouter
import data.database.initDatabase
import features.firebase.FirebaseConfig
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import plugins.configureRouting
import plugins.configureSerialization

fun main() {
    embeddedServer(CIO, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    initDatabase()
    FirebaseConfig.init()
    configureSerialization()
    configureRouting()
    configureLoginRouter()
    configureRegisterRouter()
}