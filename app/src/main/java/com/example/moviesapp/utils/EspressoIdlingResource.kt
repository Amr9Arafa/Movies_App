package com.example.moviesapp.utils

import androidx.test.espresso.idling.CountingIdlingResource

object EspressoIdleResource {
    private const val RESOURCE = "GLOBAL"

    @JvmField
    val countingIdlingResource = CountingIdlingResource(RESOURCE)

    fun increment() {
        countingIdlingResource.increment()
    }

    fun decrement() {
        if (!countingIdlingResource.isIdleNow) {
            countingIdlingResource.decrement()
        }
    }


}

inline fun <T> wrapEspressoIdlingResource(function: () -> T): T {
    // Espresso does not work well with coroutines yet. See
    // https://github.com/Kotlin/kotlinx.coroutines/issues/982
    EspressoIdleResource.increment() // Set app as busy.
    return try {
        function()
    } finally {
        EspressoIdleResource.decrement() // Set app as idle.
    }
}