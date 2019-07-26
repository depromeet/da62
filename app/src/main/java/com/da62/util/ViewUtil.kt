package com.da62.util

import android.app.Activity
import org.jetbrains.anko.alert

fun Activity.waterDialog(content: String) {
    alert {
        title = "물주기"
        message = content
        positiveButton("물주기") {

        }
        negativeButton("나중에") {

        }
    }.show()
}