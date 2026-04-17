package io.illusion

import features.login.LoginReceive
import kotlinx.serialization.Serializable

object UserStorage {
    val users = mutableListOf<LoginReceive>()
}