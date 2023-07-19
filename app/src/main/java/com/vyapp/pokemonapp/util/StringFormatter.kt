package com.vyapp.pokemonapp.util

fun toKg(weight: Int): Float = weight.toFloat() / 10
fun toCm(height: Int): Float = height.toFloat() * 10

fun <T> infoStringFormat(
    param: T,
    unit: String? = null,
    description: String? = null
): String {
    return when (unit) {
        KG -> "$description: ${toKg((param as Int))} $unit"
        CM -> "$description: ${toCm((param as Int))} $unit"
        else -> "$description: $param"
    }
}
