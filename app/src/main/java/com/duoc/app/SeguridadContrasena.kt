
package com.duoc.app

import android.util.Base64
import java.security.MessageDigest
import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

object SeguridadContrasena {

    fun generarSalt(): String {
        val bytes = ByteArray(16)
        SecureRandom().nextBytes(bytes)
        return Base64.encodeToString(bytes, Base64.NO_WRAP)
    }

    fun generarHash(contrasena: String, salt: String): String {
        val saltBytes = Base64.decode(salt, Base64.NO_WRAP)
        val especificacion = PBEKeySpec(
            contrasena.toCharArray(),
            saltBytes,
            120000,
            256
        )

        return try {
            val fabrica = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
            val hash = fabrica.generateSecret(especificacion).encoded
            Base64.encodeToString(hash, Base64.NO_WRAP)
        } finally {
            especificacion.clearPassword()
        }
    }

    fun verificar(
        contrasena: String,
        salt: String,
        hashGuardado: String
    ): Boolean {
        val hashIngresado = generarHash(contrasena, salt)

        return MessageDigest.isEqual(
            Base64.decode(hashIngresado, Base64.NO_WRAP),
            Base64.decode(hashGuardado, Base64.NO_WRAP)
        )
    }
}
