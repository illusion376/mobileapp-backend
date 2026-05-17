package helpers

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException

object FirebaseAdminService {

    fun checkUserStatus(email: String): UserStatus {
        return try {
            val userRecord = FirebaseAuth.getInstance().getUserByEmail(email)
            if (userRecord.isEmailVerified) {
                UserStatus.REGISTERED
            } else {
                UserStatus.REQUIRES_CONFIRMATION
            }
        } catch (e: FirebaseAuthException) {
            println("Firebase Auth Error: ${e.message}")
            UserStatus.NOT_FOUNDED
        } catch (e: Exception) {
            println("General Error in FirebaseAdminService: ${e.message}")
            UserStatus.ERROR
        }
    }
}

enum class UserStatus{
    REGISTERED,
    REQUIRES_CONFIRMATION,
    NOT_FOUNDED,
    ERROR
}