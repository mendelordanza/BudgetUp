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
}