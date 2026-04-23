package features.firebase

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions

object FirebaseConfig {
    const val WEB_API_KEY = "AIzaSyD2oaXud9EbB4uwhu5AesX5e27RVnJYEIA"
    const val AUTH_REST_URL = "https://identitytoolkit.googleapis.com/v1/accounts"

    fun init() {
        val serviceAccount = object {}.javaClass.classLoader.getResourceAsStream("firebase-key.json")
            ?: throw Exception("Файл firebase-key.json не найден в src/main/resources")

        val options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build()

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options)
        }
    }
}