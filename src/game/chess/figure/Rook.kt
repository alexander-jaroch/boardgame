package com.boardgame.game.chess.figure

import com.boardgame.game.vector.Direction
import com.boardgame.game.vector.IntVector2D
import com.boardgame.game.Side
import com.boardgame.game.Board

class Rook(side: Side, board: Board) : Figure(side, board) {
    override val name: String = "Rook"
    override val range: Int = -1

    override val movePatterns: Array<IntVector2D> = arrayOf(
        Direction.Forward.vector,
        Direction.Right.vector,
        Direction.Backward.vector,
        Direction.Left.vector
    )
    override val capturePatterns: Array<IntVector2D> = movePatterns
}