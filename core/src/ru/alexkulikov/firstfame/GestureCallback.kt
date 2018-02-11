package ru.alexkulikov.firstfame

interface GestureCallback {
    fun onTap(power: Float)
    fun jump(power: Float)
    fun tap()
}