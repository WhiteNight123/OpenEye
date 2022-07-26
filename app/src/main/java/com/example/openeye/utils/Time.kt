package com.example.openeye.utils

/**
 * 将秒转换成几分几秒
 */
fun getTime(second: Int): String {
    var tmp = ""
    if (second >= 600) {
        tmp = if (second % 60 >= 10) {
            tmp + (second / 60).toString() + ":" + (second % 60)
        } else {
            tmp + (second / 60).toString() + ":" + "0" + (second % 60)
        }
    } else if (second >= 60) {
        tmp = if (second % 60 >= 10) {
            tmp + "0" + (second / 60).toString() + ":" + (second % 60)
        } else {
            tmp + "0" + (second / 60).toString() + ":" + "0" + (second % 60)
        }
    } else {
        tmp = if (second % 60 >= 10) {
            "00:" + (second % 60).toString()
        } else {
            "00:" + "0" + (second % 60).toString()
        }
    }
    return tmp
}
