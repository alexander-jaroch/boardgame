package com.boardgame.game.chess

import com.boardgame.game.vector.IntVector2D
import com.boardgame.game.Side
import com.boardgame.game.Board
import com.boardgame.game.Game
import com.boardgame.game.chess.figure.*
import kotlinx.serialization.Serializable

@Serializable
class ChessData(
    val figures: List<FigureData>,
    val turn: Int,
    val side: Side
)

@Serializable
class ChessSaveData(
    val figures: List<FigureData>,
    val turn: Int,
    val side: Side,
    val gameId: String,
    val playerIds: Array<String>
)

class Chess : Game(2, 2) {
    val board: Board = Board(8, 8)

    private var side: Side = Side.White
    private val king: King
        get() = board.data.find { figure -> figure is King && figure.side == side } as King

    private var data: ChessSaveData = save()

    override fun init(gameId: String, hostId: String) {
        super.init(gameId, hostId)

        for (i in 0 until board.width) {
            Pawn(Side.White, board).init(i, 1)
            Pawn(Side.Black, board).init(i, 6)
        }

        Rook(Side.White, board).init(0, 0)
        Rook(Side.Black, board).init(0, 7)
        Rook(Side.White, board).init(7, 0)
        Rook(Side.Black, board).init(7, 7)

        Bishop(Side.White, board).init(1, 0)
        Bishop(Side.Black, board).init(1, 7)
        Bishop(Side.White, board).init(6, 0)
        Bishop(Side.Black, board).init(6, 7)

        Knight(Side.White, board).init(2, 0)
        Knight(Side.Black, board).init(2, 7)
        Knight(Side.White, board).init(5, 0)
        Knight(Side.Black, board).init(5, 7)

        Queen(Side.White, board).init(3, 0)
        King(Side.Black, board).init(3, 7)
        King(Side.White, board).init(4, 0)
        Queen(Side.Black, board).init(4, 7)
    }

    fun move(origin: IntVector2D, target: IntVector2D): Boolean {
        data = save()
        val figure: Figure? = board[origin]
        if (figure != null && side == figure.side) {
            if (figure.move(target) || figure.capture(target)) {
                if (check()) {
                    board.clear()
                    init(data)
                    return false
                }
                endTurn()
                return true
            }
        }
        return false
    }

    private fun check(): Boolean {
        return check(king.position)
    }

    private fun check(position: IntVector2D): Boolean {
        for (figure in board.data.filter { figure -> figure?.side !== side }) {
            //println("${figure?.side} ${figure?.name} ${figure?.position} --> ${position}: ${figure?.canCapture(position)}")
            if (figure != null && figure.canCapture(position)) {
                println("$gameId: CHECK")
                return true
            }
        }
        return false
    }

    private fun checkMate(): Boolean {
        if (!check()) {
            return false
        }
        for (pattern in king.movePatterns) {
            if (!check(king.position + pattern * king.side.scale)) {
                return false
            }
        }
        return check()
    }

    private fun endTurn() {
        turn++
        side = if (side == Side.White) Side.Black else Side.White
        if (checkMate()) {
            println("$gameId: CHECK MATE")
        }
    }

    fun data(): ChessData {
        val figures: List<Figure> = board.data.filterNotNull()
        return ChessData(figures.map { figure -> figure.data() }, turn, side)
    }

    fun save(): ChessSaveData {
        val figures: List<Figure> = board.data.filterNotNull()
        return ChessSaveData(figures.map { figure -> figure.data() }, turn, side, gameId, playerIds.toTypedArray())
    }

    fun init(data: ChessSaveData) {
        super.init(data.gameId, data.playerIds.first())
        side = data.side
        turn = data.turn
        for (figure in data.figures) {
            when (figure.name) {
                "Pawn" -> Pawn(figure.side, board).init(figure.position.x, figure.position.y)
                "Rook" -> Rook(figure.side, board).init(figure.position.x, figure.position.y)
                "Bishop" -> Bishop(figure.side, board).init(figure.position.x, figure.position.y)
                "Knight" -> Knight(figure.side, board).init(figure.position.x, figure.position.y)
                "Queen" -> Queen(figure.side, board).init(figure.position.x, figure.position.y)
                "King" -> King(figure.side, board).init(figure.position.x, figure.position.y)
            }
        }
        data.playerIds.forEach { id -> playerIds.add(id) }
    }
}