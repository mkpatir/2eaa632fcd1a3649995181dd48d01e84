package com.mkpatir.spacedelivery.internal.extension

fun Int?.oneOrThis(): Int = this?.let { if (this < 1) 1 else this } ?: 1

fun Int?.isNullOrZero(): Boolean = this?.let { this == 0 } ?: true

fun Int?.orZero(): Int = this ?: 0