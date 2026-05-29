package io.illusion.helpers

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.Date

object TokenService {
    public const val SECRET = "winline-free_bet-3000"
    public const val ISSUER = "io.illusion"
    public const val AUDIENCE = "mobile-app"

    fun generateToken(userId: Int, email: String): String {
        return JWT.create()
            .withIssuer(ISSUER)
            .withAudience(AUDIENCE)
            .withClaim("userId", userId.toString())
            .withClaim("email", email)
            .withExpiresAt(Date(System.currentTimeMillis() + 3600000 * 24))
            .sign(Algorithm.HMAC256(SECRET))
    }
}