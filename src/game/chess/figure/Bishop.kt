package com.boardgame.game.chess.figure

import com.boardgame.game.vector.Direction
import com.boardgame.game.vector.IntVector2D
import com.boardgame.game.Side
import com.boardgame.game.Board

class Bishop(side: Side, board: Board) : Figure(side, board) {
    override val name: String = "Bishop"
    override val range: Int = -1

    override val movePatterns: Array<IntVector2D> = arrayOf(
        Direction.ForwardRight.vector,
        Direction.BackwardRight.vector,
        Direction.BackwardLeft.vector,
        Direction.ForwardLeft.vector
    )
    override val capturePatterns: Array<IntVector2D> = movePatterns
}