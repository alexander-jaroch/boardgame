package com.boardgame.game

import com.boardgame.game.vector.IntVector2D
import com.boardgame.game.chess.figure.Figure

class Board(
    val width: Int,
    val height: Int
) {
    var data: Array<Figure?> = arrayOfNulls(width * height)
        private set

    operator fun get(x: Int, y: Int): Figure? {
        return data[y * width + x]
    }

    operator fun get(pos: IntVector2D): Figure? {
        return this[pos.x, pos.y]
    }

    operator fun set(x: Int, y: Int, figure: Figure?) {
        setFigure(IntVector2D(x, y), figure)
    }

    operator fun set(pos: IntVector2D, figure: Figure?) {
        setFigure(pos, figure)
    }

    fun hasPos(pos: IntVector2D): Boolean {
        return pos.x >= 0 && pos.y >= 0 && pos.x < width && pos.y < height
    }

    fun clear() {
        data = arrayOfNulls(width * height)
    }

    private fun setFigure(pos: IntVector2D, figure: Figure?) {
        data[pos.y * width + pos.x] = figure
    }

    override fun toString(): String {
        var len: Int = data.maxOf { figure -> figure.toString().length }

        return data.mapIndexed { i, figure ->
            (if (i % width == 0) "\n" else "") + figure.toString().padEnd(len, ' ')
        }.joinToString("\t")
    }
}