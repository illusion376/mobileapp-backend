package helpers

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException

object FirebaseAdminService {

    fun checkUserStatus(email: String): String {
        return try {
            val userRecord = FirebaseAuth.getInstance().getUserByEmail(email)
            if (userRecord.isEmailVerified) {
                "зарегистрирован"
            } else {
                "требует подтверждения"
            }
        } catch (e: FirebaseAuthException) {
            println("Firebase Auth Error: ${e.message}")
            "не найден"
        } catch (e: Exception) {
            println("General Error in FirebaseAdminService: ${e.message}")
            "ошибка"
        }
    }
}