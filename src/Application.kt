package com.boardgame

import com.boardgame.game.GameManager
import com.boardgame.route.gameRoutes
import io.ktor.application.*
import io.ktor.routing.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

var gameManager = GameManager()

@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    routing {
        gameRoutes()
    }
}

