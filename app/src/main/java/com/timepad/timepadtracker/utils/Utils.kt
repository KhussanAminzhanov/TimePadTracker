package com.timepad.timepadtracker.utils

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import java.time.LocalDate
import java.util.*

private val cal = Calendar.getInstance().apply { time = Date() }

fun getCurrentDayOfYear(): Int = cal.get(Calendar.DAY_OF_YEAR)

fun getCurrentHourOfDay(): Int = cal.get(Calendar.HOUR_OF_DAY)

fun getCurrentDayOfWeek(): Int = cal.get(Calendar.DAY_OF_WEEK)

fun getCurrentDaySinceEpoch(): Long = LocalDate.now().toEpochDay()

fun getScreenWidth(): Int {
    return Resources.getSystem().displayMetrics.widthPixels
}

fun getScreenHeight(): Int {
    return Resources.getSystem().displayMetrics.heightPixels
}

@ColorInt
fun Context.getColorFromAttr(
    @AttrRes attrColor: Int,
    typedValue: TypedValue = TypedValue(),
    resolveRefs: Boolean = true
): Int {
    theme.resolveAttribute(attrColor, typedValue, resolveRefs)
    return typedValue.data
}