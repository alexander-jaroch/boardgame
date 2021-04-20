package com.boardgame.figure

import com.boardgame.board.Board
import com.boardgame.Side
import com.boardgame.IntVector2D

abstract class Figure(
    val side: Side,
    val board: Board
) {
    var pos: IntVector2D = IntVector2D()
    var turn: Int = 0

    abstract val name: String
    abstract val movePatterns: Array<IntVector2D>
    abstract val capturePatterns: Array<IntVector2D>

    abstract fun move(target: IntVector2D): Boolean
    abstract fun canMove(target: IntVector2D): Boolean
    abstract fun capture(target: IntVector2D): Boolean
    abstract fun canCapture(target: IntVector2D): Boolean

    override fun toString(): String {
        return side.name + " " + name
    }
}