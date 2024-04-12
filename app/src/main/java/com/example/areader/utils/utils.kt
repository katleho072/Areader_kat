package com.example.areader.utils

import android.icu.text.DateFormat

fun formatDates(timestamp: java.sql.Timestamp?): String {
    val date = DateFormat.getDateInstance()
        .format(timestamp?.date)
        .toString().split(",") [0]// march 12
    return date
}
