package com.tylerlowrey.plugins


import com.tylerlowrey.routes.commandRouting
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        commandRouting()
    }
}