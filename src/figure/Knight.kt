package com.boardgame.figure

import com.boardgame.IntVector2D
import com.boardgame.Side
import com.boardgame.board.Board

class Knight(side: Side, board: Board) : Figure(side, board) {
    override val name: String = "Knight"

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

    override fun move(target: IntVector2D): Boolean {
        if (!canMove(target))
            return false
        board[target] = this
        return true
    }

    override fun canMove(target: IntVector2D): Boolean {
        TODO("Not yet implemented")
    }

    override fun capture(target: IntVector2D): Boolean {
        if (!canCapture(target))
            return false
        board[target] = this
        return true
    }

    override fun canCapture(target: IntVector2D): Boolean {
        TODO("Not yet implemented")
    }
}