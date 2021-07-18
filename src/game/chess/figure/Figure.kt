package com.boardgame.game.chess.figure

import com.boardgame.game.Board
import com.boardgame.game.Side
import com.boardgame.game.vector.IntVector2D
import kotlinx.serialization.Serializable
import kotlin.math.max

@Serializable
data class FigureData(val side: Side, val name: String, val position: IntVector2D)

abstract class Figure(
    val side: Side,
    val board: Board
) {
    var position: IntVector2D = IntVector2D()
        set(pos) {
            board[pos] = this
            field = pos
        }

    abstract val name: String
    abstract val range: Int
    abstract val movePatterns: Array<IntVector2D>
    abstract val capturePatterns: Array<IntVector2D>

    private val safeRange
        get() = if (range == -1) max(board.width, board.height) else range

    fun init(x: Int, y: Int) {
        position = IntVector2D(x, y)
    }

    open fun canMove(pos: IntVector2D): Boolean {
        if (!canTarget(movePatterns, pos))
            return false
        if (board[pos] != null)
            return false
        return true
    }

    open fun move(pos: IntVector2D): Boolean {
        return target(pos, canMove(pos))
    }

    open fun canCapture(pos: IntVector2D): Boolean {
        if (!canTarget(capturePatterns, pos))
            return false
        if (board[pos] == null || board[pos]?.side == side)
            return false
        return true
    }

    open fun capture(pos: IntVector2D): Boolean {
        return target(pos, canCapture(pos))
    }

    fun data(): FigureData {
        return FigureData(side, name, position)
    }

    protected open fun canTarget(patterns: Array<IntVector2D>, pos: IntVector2D): Boolean {
        val pattern: IntVector2D = (pos - position) * side.scale
        if (!board.hasPos(pos))
            return false
        val targetPattern: IntVector2D =
            patterns.find { item -> pattern.multipleOf(item) && pattern.length <= (item * safeRange).length }
                ?: return false

        val targetPattern2: IntVector2D = patterns.find { item -> pattern.multipleOf(item) && pattern.length <= (item * safeRange).length }
            ?: return false

        var preview: IntVector2D = position + targetPattern * side.scale
        while (preview != pos) {
            if (!board.hasPos(preview) || board[preview] !== null)
                return false
            preview += targetPattern * side.scale
        }
        return true
    }

    protected open fun target(pos: IntVector2D, condition: Boolean): Boolean {
        if (!condition)
            return false
        board[position] = null
        position = pos
        return true
    }
}