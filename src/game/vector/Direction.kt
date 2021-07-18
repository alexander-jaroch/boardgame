package com.boardgame.game.vector

enum class Direction(
    val vector: IntVector2D
) {
    Forward(IntVector2D(0, 1)),
    ForwardRight(IntVector2D(1, 1)),
    Right(IntVector2D(1, 0)),
    BackwardRight(IntVector2D(1, -1)),
    Backward(IntVector2D(0, -1)),
    BackwardLeft(IntVector2D(-1, -1)),
    Left(IntVector2D(-1, 0)),
    ForwardLeft(IntVector2D(-1, 1))
}