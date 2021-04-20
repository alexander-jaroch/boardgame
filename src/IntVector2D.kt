package com.boardgame

data class IntVector2D(
    val x: Int,
    val y: Int
) {
    constructor() : this(0, 0)

    operator fun plus(other: IntVector2D): IntVector2D {
        return IntVector2D(x + other.x, y + other.y)
    }

    operator fun minus(other: IntVector2D): IntVector2D {
        return IntVector2D(x - other.x, y - other.y)
    }

    operator fun times(s: Int): IntVector2D {
        return IntVector2D(x * s, y * s)
    }

    operator fun div(d: Int): IntVector2D {
        return IntVector2D(x / d, y / d)
    }

    override operator fun equals(other: Any?): Boolean {
        return other is IntVector2D && x == other.x && y == other.y
    }
}