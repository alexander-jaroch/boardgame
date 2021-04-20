package com.boardgame.game

import com.boardgame.Side
import com.boardgame.board.Board
import com.boardgame.figure.*

class Chess() {
    val board: Board = Board(8, 8)

    fun init() {
        for (i in 0 until board.width) {
            board[i, 1] = Pawn(Side.White, board)
            board[i, 6] = Pawn(Side.Black, board)
        }

        board[0, 0] = Rook(Side.White, board)
        board[0, 7] = Rook(Side.Black, board)
        board[7, 0] = Rook(Side.White, board)
        board[7, 7] = Rook(Side.Black, board)

        board[1, 0] = Bishop(Side.White, board)
        board[1, 7] = Bishop(Side.Black, board)
        board[6, 0] = Bishop(Side.White, board)
        board[6, 7] = Bishop(Side.Black, board)

        board[2, 0] = Knight(Side.White, board)
        board[2, 7] = Knight(Side.Black, board)
        board[5, 0] = Knight(Side.White, board)
        board[5, 7] = Knight(Side.Black, board)

        board[3, 0] = Queen(Side.White, board)
        board[3, 7] = King(Side.Black, board)

        board[4, 0] = King(Side.White, board)
        board[4, 7] = Queen(Side.Black, board)

        println(board)
    }
}