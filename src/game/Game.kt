package com.boardgame.game

abstract class Game(
    private val minPlayerCount: Int,
    private val maxPlayerCount: Int
) {
    var gameId: String = ""
        private set
    val playerIds: MutableSet<String> = mutableSetOf()
    var locked: Boolean = false
        protected set
    var turn: Int = 0
        protected set

    open fun init(gameId: String, hostId: String) {
        this.gameId = gameId
        playerIds.add(hostId)
    }

    fun addPlayer(playerId: String): Boolean {
        if (!locked && playerIds.count() < maxPlayerCount) {
            return playerIds.add(playerId)
        }
        return false
    }

    fun lock(playerId: String): Boolean {
        if (playerIds.first() == playerId && playerIds.count() in minPlayerCount..maxPlayerCount) {
            locked = true
        }
        return locked
    }

    fun verify(playerId: String): Boolean {
        return playerIds.contains(playerId) && playerIds.indexOf(playerId) == turn % playerIds.count()
    }

    fun isHost(playerId: String): Boolean {
        return playerIds.count() > 0 && playerIds.first() == playerId
    }
}