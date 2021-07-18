package com.boardgame.game.chess.figure

import com.boardgame.game.vector.Direction
import com.boardgame.game.vector.IntVector2D
import com.boardgame.game.Side
import com.boardgame.game.Board

class Queen(side: Side, board: Board) : Figure(side, board) {
    override val name: String = "Queen"
    override val range: Int = -1

    override val movePatterns: Array<IntVector2D> = Direction.values().map { it.vector }.toTypedArray()
    override val capturePatterns: Array<IntVector2D> = movePatterns
}