package io.illusion.features.authentication

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.illusion.helpers.TokenService
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt

fun Application.configureSecurity() {
    install(Authentication) {
        jwt("auth-jwt") {
            realm = "Access to the mobile app"

            verifier(
                JWT.require(Algorithm.HMAC256(TokenService.SECRET))
                    .withAudience(TokenService.AUDIENCE)
                    .withIssuer(TokenService.ISSUER)
                    .build()
            )

            validate { credential ->
                val userId = credential.payload.getClaim("userId").asString()

                if (!userId.isNullOrBlank()) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
        }
    }
}