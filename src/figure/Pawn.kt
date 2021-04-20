package com.boardgame.figure

import com.boardgame.board.Board
import com.boardgame.Side
import com.boardgame.IntVector2D

class Pawn(side: Side, board: Board) : Figure(side, board) {
    override val name: String = "Pawn"

    override val movePatterns: Array<IntVector2D> = arrayOf(
        IntVector2D(0, 1)
    )

    override val capturePatterns: Array<IntVector2D> = arrayOf(
        IntVector2D(1, 1),
        IntVector2D(-1, 1)
    )

    override fun move(target: IntVector2D): Boolean {
        if (!canMove(target))
            return false
        board[target] = this
        turn++
        return true
    }

    override fun canMove(target: IntVector2D): Boolean {
        val pattern: IntVector2D = (target - pos) * side.direction
        if (!board.hasPos(target))
            return false
        if (!movePatterns.contains(pattern) && !(turn == 0 && pattern == IntVector2D(0, 2)))
            return false
        if (board[target] != null)
            return false
        if (turn == 0 && board[target - (pattern / 2)] != null)
            return false
        return true
    }

    override fun capture(target: IntVector2D): Boolean {
        if (!canCapture(target))
            return false
        board[target] = this
        return true
    }

    override fun canCapture(target: IntVector2D): Boolean {
        val pattern: IntVector2D = (target - pos) * side.direction
        if (!board.hasPos(target))
            return false
        if (!capturePatterns.contains(pattern))
            return false
        if (board[target] != null && board[target]?.side == side)
            return false
        return true
    }
}