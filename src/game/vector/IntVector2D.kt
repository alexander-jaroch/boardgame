package com.boardgame.game.vector

import kotlinx.serialization.Serializable
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

@Serializable
data class IntVector2D(
    val x: Int,
    val y: Int
) {
    private val epsilon: Double = 0.000000000000001

    constructor() : this(0, 0)

    val length: Double
        get() = sqrt(x.toDouble().pow(2) + y.toDouble().pow(2));

    operator fun plus(other: IntVector2D): IntVector2D {
        return IntVector2D(x + other.x, y + other.y)
    }

    operator fun minus(other: IntVector2D): IntVector2D {
        return IntVector2D(x - other.x, y - other.y)
    }

    operator fun times(scale: Int): IntVector2D {
        return IntVector2D(x * scale, y * scale)
    }

    operator fun div(scale: Int): IntVector2D {
        return IntVector2D(x / scale, y / scale)
    }

    override fun equals(other: Any?): Boolean {
        return other is IntVector2D && x == other.x && y == other.y
    }

    fun multipleOf(other: IntVector2D): Boolean {
        return abs(x / length - other.x / other.length) < epsilon && abs(y / length - other.y / other.length) < epsilon
    }

    override fun toString(): String {
        return "($x, $y)"
    }
}