package com.boardgame.game

import com.boardgame.game.chess.Chess
import com.boardgame.game.chess.ChessSaveData

class GameManager {
    private val games: MutableList<Game> = mutableListOf();
    private var currentId: Long = 0;

    private fun random(): String {
        return String.format("%.0f", Long.MAX_VALUE * Math.random())
    }

    fun newChess(): Chess {
        val chess = Chess()
        chess.init("GAME${random()}-${currentId++}", "HOST${random()}")
        games.add(chess)
        return chess
    }

    fun importChess(data: ChessSaveData): Chess {
        val chess = Chess()
        chess.init(data)
        val duplicate: Game? = games.find { game -> game.gameId == data.gameId }
        if (duplicate !== null)
            games.remove(duplicate)
        games.add(chess)
        return chess
    }

    fun join(game: Game): String? {
        val playerId: String = "GUEST${random()}"
        if (game.addPlayer(playerId)) return playerId
        return null
    }

    fun deleteGame(gameId: String) {
        val game: Game? = games.find { game -> game.gameId == gameId }
        if (game !== null)
            games.remove(game)
    }

    operator fun get(gameId: String?): Game? {
        if (gameId !== null) {
            return games.find { game -> game.gameId == gameId }
        }
        return null
    }

}