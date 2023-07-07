package com.vyapp.pokemonapp.util

fun toKg(weight: Int): Float = weight.toFloat() / 10
fun toCm(height: Int): Float = height.toFloat() * 10

//костыли
fun weightString(weight: Int) = "Weight: ${toKg(weight)} $KG"
fun heightString(height: Int) = "Height: ${toCm(height)} $CM"
fun typeString(type: String) = "Type: $type"