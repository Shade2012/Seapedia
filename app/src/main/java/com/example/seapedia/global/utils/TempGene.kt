package com.example.seapedia.global.utils

object TempIdGenerator {
    private val counter = java.util.concurrent.atomic.AtomicInteger(-1)


    fun nextId(): Int = counter.getAndDecrement()
}