package com.tokodeveloper.reaction_time.util

import android.content.Context
import com.tokodeveloper.reaction_time.R
import java.io.ByteArrayOutputStream
import java.io.IOException

fun readPrivacyFile(context: Context): String {
    val inputStream = context.resources.openRawResource(R.raw.privacy_policy)
    val byteArrayOutputStream = ByteArrayOutputStream()
    var i: Int
    try {
        i = inputStream.read()
        while (i != -1) {
            byteArrayOutputStream.write(i)
            i = inputStream.read()
        }
        inputStream.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }

    return byteArrayOutputStream.toString()
}