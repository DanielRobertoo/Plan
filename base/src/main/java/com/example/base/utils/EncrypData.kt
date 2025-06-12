package com.example.base.utils

import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object EncryptionData {

    private const val TRANSFORMATION = "AES/CBC/PKCS5Padding"
    private const val SECRET_KEY = "uenaçdo-.edswSC3"
    private const val IV = "encriptacion____"

    private fun getSecretKey(): SecretKey {
        return SecretKeySpec(SECRET_KEY.toByteArray(), "AES")
    }

    fun encrypt(input: String): String {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        val keySpec = getSecretKey()
        val ivSpec = IvParameterSpec(IV.toByteArray())
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)
        val encrypted = cipher.doFinal(input.toByteArray())
        return Base64.encodeToString(encrypted, Base64.DEFAULT)
    }

    fun decrypt(encrypted: String): String {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        val keySpec = getSecretKey()
        val ivSpec = IvParameterSpec(IV.toByteArray())
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)
        val decodedBytes = Base64.decode(encrypted, Base64.DEFAULT)
        val decrypted = cipher.doFinal(decodedBytes)
        return String(decrypted)
    }
}