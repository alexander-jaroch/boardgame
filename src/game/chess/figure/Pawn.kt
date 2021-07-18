package com.boardgame.game.chess.figure

import com.boardgame.game.vector.Direction
import com.boardgame.game.vector.IntVector2D
import com.boardgame.game.Side
import com.boardgame.game.Board

class Pawn(side: Side, board: Board) : Figure(side, board) {
    private var rangeMultiplier: Int = 2

    override val name: String = "Pawn"
    override val range: Int = 1

    override val movePatterns: Array<IntVector2D> = arrayOf(
        Direction.Forward.vector
    )
    override val capturePatterns: Array<IntVector2D> = arrayOf(
        Direction.ForwardRight.vector,
        Direction.ForwardLeft.vector
    )

    constructor(pawn: Pawn) : this(pawn.side, pawn.board) {
        rangeMultiplier = pawn.rangeMultiplier
    }

    override fun canTarget(patterns: Array<IntVector2D>, pos: IntVector2D): Boolean {
        val pattern: IntVector2D = (pos - position) * side.scale
        if (!board.hasPos(pos))
            return false
        if (patterns.find { item -> pattern.multipleOf(item) && pattern.length <= (item * range * rangeMultiplier).length } == null)
            return false
        return true
    }
}