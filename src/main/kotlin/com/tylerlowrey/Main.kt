package com.tylerlowrey

import com.pi4j.io.gpio.digital.DigitalState
import com.pi4j.ktx.console
import com.pi4j.ktx.io.digital.digitalOutput
import com.pi4j.ktx.io.digital.piGpioProvider
import com.pi4j.ktx.pi4j
import com.tylerlowrey.models.LedControlCommand
import com.tylerlowrey.plugins.configureRouting
import com.tylerlowrey.plugins.configureSerialization
import io.ktor.server.application.*
import kotlinx.coroutines.channels.Channel
import java.util.concurrent.ConcurrentLinkedQueue
import kotlin.concurrent.thread

const val LED_OUTPUT_PIN = 14

val ledCommandQueue = Channel<LedControlCommand>()

fun main(args: Array<String>) {
    println("rpi-io-controller-server starting...")
    thread {
        io.ktor.server.netty.EngineMain.main(args)
    }
    console {
        pi4j {
            digitalOutput(LED_OUTPUT_PIN) {
                id("LED")
                shutdown(DigitalState.LOW)
                initial(DigitalState.LOW)
                piGpioProvider()
            }.run {
                var ledToggleSleepLengthPercentage = 0.5
                while (true) {
                    ledToggleSleepLengthPercentage = ledCommandQueue.tryReceive().getOrNull()?.speed
                        ?: ledToggleSleepLengthPercentage
                    Thread.sleep(2000 - ledToggleSleepLengthPercentage.toLong() * 2000)
                    toggle()
                    println("LED state: ${state()}")
                    Thread.sleep(10)
                }
            }
        }
    }
}

fun Application.module() {
    configureRouting()
    configureSerialization()
}