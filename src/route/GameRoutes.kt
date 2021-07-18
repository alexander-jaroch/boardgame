package com.boardgame.route

import com.boardgame.game.Game
import com.boardgame.game.vector.IntVector2D
import com.boardgame.game.chess.figure.Figure
import com.boardgame.game.chess.Chess
import com.boardgame.game.chess.ChessData
import com.boardgame.game.chess.ChessSaveData
import com.boardgame.gameManager
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

@Serializable
data class SimpleResponse(val error: Boolean = false)

@Serializable
data class ErrorResponse(val message: String) {
    val error: Boolean = true
}

@Serializable
data class PlayerResponse(val gameId: String, val playerId: String) {
    val error: Boolean = false
}

@Serializable
data class GameResponse(val data: ChessData) {
    val error: Boolean = false
}

@Serializable
data class AuthRequest(val gameId: String, val playerId: String)

@Serializable
data class MoveRequest(val auth: AuthRequest, val from: IntVector2D, val to: IntVector2D)

fun Route.gameRoutes() {
    get("host") {
        val chess = gameManager.newChess()
        val response = PlayerResponse(chess.gameId, chess.playerIds.first())
        call.respondText(Json.encodeToString(response), ContentType.Application.Json)
    }

    get("join/{id}") {
        val game: Chess? = gameManager[call.parameters["id"]] as Chess?
        if (game !== null) {
            val playerId: String? = gameManager.join(game)
            if (playerId !== null)
                call.respondText(
                    Json.encodeToString(PlayerResponse(game.gameId, playerId)),
                    ContentType.Application.Json
                )
            else
                call.respondText(Json.encodeToString(ErrorResponse("Session full")), ContentType.Application.Json)
        } else
            call.respondText(Json.encodeToString(ErrorResponse("Game not found")), ContentType.Application.Json)
    }

    get("start") {
        val request: AuthRequest = Json.decodeFromString(call.receiveText())
        val game: Chess? = gameManager[request.gameId] as Chess?
        if (game !== null && game.lock(request.playerId))
            call.respondText(Json.encodeToString(SimpleResponse()), ContentType.Application.Json)
        else
            call.respondText(Json.encodeToString(ErrorResponse("Could not start game")), ContentType.Application.Json)
    }

    get("board") {
        val request: AuthRequest = Json.decodeFromString(call.receiveText())
        val game: Chess? = gameManager[request.gameId] as Chess?
        if (game !== null)
            call.respondText(Json.encodeToString(game.data()), ContentType.Application.Json)
        else
            call.respondText(Json.encodeToString(ErrorResponse("Game not found")), ContentType.Application.Json)
    }

    get("export") {
        val request: AuthRequest = Json.decodeFromString(call.receiveText())
        val game: Chess? = gameManager[request.gameId] as Chess?
        if (game !== null && game.isHost(request.playerId))
            call.respondText(Json.encodeToString(game.save()), ContentType.Application.Json)
        else
            call.respondText(Json.encodeToString(ErrorResponse("No permission to save game")), ContentType.Application.Json)
    }

    get("image") {
        val request: AuthRequest = Json.decodeFromString(call.receiveText())
        val game: Chess? = gameManager[request.gameId] as Chess?
        if (game !== null) {
            val image: BufferedImage = BufferedImage(816, 816, 10)

            for (x in 0 until game.board.width) {
                for (y in 0 until game.board.height) {
                    val figure: Figure? = game.board[IntVector2D(x, y)]
                    if (figure != null) {
                        val path = figure.side.toString().toLowerCase() + figure.name + ".jpg"
                        val piece = ImageIO.read(File("resources/pieces/$path"))
                        for (i in 0 until 100) {
                            for (j in 0 until 100) {
                                image.setRGB(x * 102 + 1 + i, y * 102 + 1 + j, piece.getRGB(i, j))
                            }
                        }
                    } else {
                        for (i in 0 until 100) {
                            for (j in 0 until 100) {
                                image.setRGB(x * 102 + 1 + i, y * 102 + 1 + j, Int.MAX_VALUE)
                            }
                        }
                    }
                }
            }

            val file = File("image.png")
            ImageIO.write(image, "PNG", file)
            call.respondFile(file)
        } else {
            call.respondText(Json.encodeToString(ErrorResponse("Game not found")), ContentType.Application.Json)
        }
    }

    post("move") {
        val request: MoveRequest = Json.decodeFromString(call.receiveText())
        val game: Chess? = gameManager[request.auth.gameId] as Chess?
        if (game !== null && !(game as Game).locked) {
            if (game.verify(request.auth.playerId)) {
                if (game.move(request.from, request.to))
                    call.respondText(Json.encodeToString(SimpleResponse()), ContentType.Application.Json)
                else
                    call.respondText(Json.encodeToString(ErrorResponse("Invalid move")), ContentType.Application.Json)
            } else {
                call.respondText(Json.encodeToString(ErrorResponse("No permission")), ContentType.Application.Json)
            }
        } else {
            call.respondText(Json.encodeToString(ErrorResponse("Game not found")), ContentType.Application.Json)
        }
    }

    post("import") {
        val request: ChessSaveData = Json.decodeFromString(call.receiveText())
        gameManager.importChess(request)
        call.respondText(Json.encodeToString(SimpleResponse()), ContentType.Application.Json)
    }
}