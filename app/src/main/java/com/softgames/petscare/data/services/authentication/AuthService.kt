package com.softgames.petscare.data.services.authentication

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class AuthService @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val database: FirebaseFirestore,
) {

    fun loadUser() = firebaseAuth.currentUser

    suspend fun signInPetscare(credential: AuthCredential): AuthResult =
        withContext(Dispatchers.IO) { firebaseAuth.signInWithCredential(credential).await() }

    suspend fun checkIfUserExist(userId: String): Boolean = withContext(Dispatchers.IO) {
        database.collection("Users").document(userId).get().await().exists()
    }

    suspend fun checkUserType(userId: String): String = withContext(Dispatchers.IO) {
        database.collection("Users").document(userId).get().await().getString("Type")!!
    }


    fun getError(errorCode: String): String {
        return when (errorCode) {
            "ERROR_INVALID_CREDENTIAL" -> "La credencial de autenticación proporcionada tiene un formato incorrecto o ha caducado."
            "ERROR_INVALID_EMAIL" -> "La dirección de correo electrónico tiene un formato incorrecto."
            "ERROR_WRONG_PASSWORD" -> "La contraseña introducida es incorrecta."
            "ERROR_USER_MISMATCH" -> "Las credenciales proporcionadas no corresponden al usuario que inició sesión anteriormente."
            "ERROR_REQUIRES_RECENT_LOGIN" -> "Esta operación es confidencial y requiere autenticación reciente. Vuelva a iniciar sesión antes de volver a intentar esta solicitud."
            "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL" -> "Ya existe una cuenta con la misma dirección de correo electrónico pero con diferentes credenciales de inicio de sesión. Inicie sesión con un proveedor asociado con esta dirección de correo electrónico"
            "ERROR_EMAIL_ALREADY_IN_USE" -> "La dirección de correo electrónico ya está en uso por otra cuenta."
            "ERROR_CREDENTIAL_ALREADY_IN_USE" -> "Esta credencial ya está asociada con una cuenta de usuario diferente."
            "ERROR_USER_DISABLED" -> "La cuenta de usuario ha sido deshabilitada por un administrador."
            "ERROR_USER_TOKEN_EXPIRED" -> "La credencial del usuario ya no es válida. El usuario debe iniciar sesión nuevamente."
            "ERROR_USER_NOT_FOUND" -> "No existe una cuenta asociada al correo electronico ingresado"
            "ERROR_INVALID_USER_TOKEN" -> "La credencial del usuario ya no es válida. El usuario debe iniciar sesión nuevamente."
            "ERROR_WEAK_PASSWORD" -> "La contraseña proporcionada no es válida."
            "ERROR_MISSING_EMAIL" -> "Se debe proporcionar una dirección de correo electrónico."
            "NO_INTERNET_CONEXION" -> "No cuenta con conexión a internet, conectese a una red y vuelva a intentarlo."
            else -> "Error desconocido."
        }
    }

}