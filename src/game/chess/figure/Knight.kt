package com.boardgame.game.chess.figure

import com.boardgame.game.vector.IntVector2D
import com.boardgame.game.Side
import com.boardgame.game.Board

class Knight(side: Side, board: Board) : Figure(side, board) {
    override val name: String = "Knight"
    override val range: Int = 1

    override val movePatterns: Array<IntVector2D> = arrayOf(
        IntVector2D(2, 1),
        IntVector2D(2, -1),
        IntVector2D(1, 2),
        IntVector2D(1, -2),
        IntVector2D(-2, 1),
        IntVector2D(-2, -1),
        IntVector2D(-1, 2),
        IntVector2D(-1, -2)
    )
    override val capturePatterns: Array<IntVector2D> = movePatterns
}