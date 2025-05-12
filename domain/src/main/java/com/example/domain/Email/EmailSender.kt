package com.example.domain.Email

import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import javax.swing.UIManager.put


fun enviarCodigoPorEmail(
    toEmail: String,
    toName: String,
    codigo: String,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
) {
    val client = OkHttpClient()

    val json = JSONObject().apply {
        put("service_id", "service_b8h85lc")
        put("template_id", "template_sz01llc")
        put("user_id", "5ltpZTxE4r3p522R-") // También llamado "public key"
        put("template_params", JSONObject().apply {
            put("title", toName)
            put("code", codigo)
        })
    }

    val body = json.toString().toRequestBody("application/json".toMediaTypeOrNull())

    val request = Request.Builder()
        .url("https://api.emailjs.com/api/v1.0/email/send")
        .post(body)
        .addHeader("origin", "http://localhost")
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            onError("Error: ${e.message}")
        }

        override fun onResponse(call: Call, response: Response) {
            if (response.isSuccessful) {
                onSuccess()
            } else {
                onError("Error al enviar: ${response.message}")
            }
        }
    })
}
