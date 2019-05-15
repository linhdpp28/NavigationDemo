package linhdo.inface

import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

/***
 * @author: Lac
 * 15-May-19 - 9:51 PM
 */

private const val MUOI = "linhdoinface"

class HashString {
    private val aesKey = SecretKeySpec(MUOI.toByteArray(), "AES")
    private val cipher = Cipher.getInstance("AES/CBC/NoPadding")

    private var instance: HashString? = null

    fun encrypt(text: String): String {
        cipher.init(Cipher.ENCRYPT_MODE, aesKey)
        return cipher.doFinal(text.toByteArray()).toString()
    }

    private fun decrypt(text: String): String {
        cipher.init(Cipher.DECRYPT_MODE, aesKey)
        return cipher.doFinal(text.toByteArray()).toString()
    }

    init {
        fun getInstance(): HashString = instance ?: HashString()
    }
}