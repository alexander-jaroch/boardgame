package com.boardgame.board

import com.boardgame.IntVector2D
import com.boardgame.figure.Figure

class Board(
    val width: Int,
    val height: Int
) {
    private val data: Array<Figure?> = arrayOfNulls(width * height)
    private var turn: Int = 0

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

    fun firstTurn(): Boolean {
        return turn == 0
    }

    fun evenTurn(): Boolean {
        return turn % 2 == 0
    }

    fun oddTurn(): Boolean {
        return turn % 2 == 1
    }

    fun hasPos(pos: IntVector2D): Boolean {
        return pos.x >= 0 && pos.y >= 0 && pos.x < width && pos.y < height
    }

    private fun setFigure(pos: IntVector2D, figure: Figure?) {
        if (figure != null)
            data[figure.pos.y * width + figure.pos.x] = null
        figure?.pos = pos
        data[pos.y * width + pos.x] = figure
    }

    override fun toString(): String {
        var len: Int = data.maxOf { figure -> figure.toString().length }

        return data.mapIndexed { i, figure ->
            (if (i % width == 0) "\n" else "") + figure.toString().padEnd(len, ' ')
        }.joinToString("\t")
    }
}