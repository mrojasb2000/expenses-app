package com.bramworks.tech.expenses.testing

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.test.platform.app.InstrumentationRegistry
import android.os.Looper
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS
): T {
    var data: T? = null
    val latch = CountDownLatch(1)

    val observer = object : Observer<T> {
        override fun onChanged(value: T) {
            data = value
            latch.countDown()
            runOnMainThread {
                this@getOrAwaitValue.removeObserver(this)
            }
        }
    }

    runOnMainThread {
        this.observeForever(observer)
    }

    if (!latch.await(time, timeUnit)) {
        runOnMainThread {
            this.removeObserver(observer)
        }
        throw TimeoutException("LiveData value was never set.")
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}

private fun runOnMainThread(block: () -> Unit) {
    if (Looper.getMainLooper().isCurrentThread) {
        block()
    } else {
        InstrumentationRegistry.getInstrumentation().runOnMainSync(block)
    }
}

