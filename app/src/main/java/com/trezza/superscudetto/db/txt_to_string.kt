package com.trezza.superscudetto.db

import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.nio.charset.Charset

/* convertire file contenuti nella cartella raw con istruzioni sql in stringhe */
fun InputStream.asString(charset: Charset = Charsets.UTF_8): String {
  this.use {
    val baos = ByteArrayOutputStream()
    val buffer = ByteArray(1024)
    var bytesRead = it.read(buffer)
    while (bytesRead > 0) {
      baos.write(buffer, 0, bytesRead)
      bytesRead = it.read(buffer)
    }
    val result = String(baos.toByteArray(), charset)
    baos.close()
    return result
  }
}