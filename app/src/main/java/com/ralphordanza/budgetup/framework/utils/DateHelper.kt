package com.ralphordanza.budgetup.framework.utils

import android.annotation.SuppressLint
import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateHelper {

    @SuppressLint("SimpleDateFormat")
    fun parseDate(inputFormat: String, outputFormat: String, date: String): String {
        var output = ""
        val inFormat = SimpleDateFormat(inputFormat)
        val outFormat = SimpleDateFormat(outputFormat)
        try {
            val d = inFormat.parse(date)
            output = outFormat.format(d)
        } catch (e: ParseException) {
            Log.e("StringHelper", e.message!!)
        }
        return output
    }

    @SuppressLint("SimpleDateFormat")
    fun parseTimestamp(date: String): Date? {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        return try {
            format.parse(date)
        } catch (e: ParseException) {
            Log.e("StringHelper", e.message!!)
            null
        }
    }
}