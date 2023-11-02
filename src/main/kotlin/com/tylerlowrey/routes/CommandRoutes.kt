package com.tylerlowrey.routes

import com.tylerlowrey.ledCommandQueue
import com.tylerlowrey.models.LedControlCommand
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.commandRouting() {
    route("/commands/ledControl") {
        post {
            val ledControlCommand = try {
                call.receive<LedControlCommand>()
            } catch (ex: ContentTransformationException) {
                println("Unable to parse LedControlCommand: ${ex.message}")
                return@post call.respondText("Invalid command sent", status = HttpStatusCode.BadRequest)
            }

            if (!(ledControlCommand.speed > 0 && ledControlCommand.speed < 1)) {
                return@post call.respondText("Speed ${ledControlCommand.speed} is invalid",
                    status = HttpStatusCode.BadRequest)
            }

            ledCommandQueue.send(ledControlCommand)
            return@post call.respondText("Successfully processed command", status = HttpStatusCode.OK)
        }
    }
}