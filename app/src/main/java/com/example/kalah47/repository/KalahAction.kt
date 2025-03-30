package com.example.kalah47.repository

import kotlin.math.floor

class KalahAction {
    companion object {
        fun makeMove(pits: MutableList<Int>, chosenPitId: Int, side: Int): Int {
//          * Side may be only 0 or 1
            if (side !in 0..1) {
                throw IllegalArgumentException("KalahAction->makeMove->side may be only '0' or '1'")
            }

            val lastId = move(pits, chosenPitId, side)
            return lastId
        }

        fun makeMoveWithImmutableList(
            pits: List<MutableList<Int>>,
            chosenPitId: Int,
            side: Int
        ): List<MutableList<Int>> {
//          * Side may be only 0 or 1
            if (side !in 0..1) {
                throw IllegalArgumentException("KalahAction->makeMove->side may be only '0' or '1'")
            }

            return moveWithList(pits, chosenPitId, side)
        }

        fun getKalahId(pitsCount: Int, side: Int): Int {
            return if (side == 0) {
                (pitsCount / 2) - 1
            } else {
                pitsCount - 1
            }
        }

        fun defineWinnerIfGameEnd(pits: MutableList<Int>, jewelsCount: Int): Int {
            var kalah0Jewels = pits[getKalahId(pits.size, 0)]
            var kalah1Jewels = pits[getKalahId(pits.size, 1)]


            val player0PitsJewels: Int = pits.subList(0, getKalahId(pits.size, 0)).sum()
            val player1PitsJewels: Int =
                pits.subList(getKalahId(pits.size, 0) + 1, getKalahId(pits.size, 1)).sum()

            if (player0PitsJewels == 0) {
                kalah1Jewels += player1PitsJewels
            }
            if (player1PitsJewels == 0) {
                kalah0Jewels += player0PitsJewels
            }

            val winner = defineWinner(kalah0Jewels, kalah1Jewels, jewelsCount)

            return winner
        }

        private fun defineWinner(kalah0Jewels: Int, kalah1Jewels: Int, jewelsCount: Int): Int {
            if (kalah0Jewels == kalah1Jewels && kalah0Jewels == floor(jewelsCount / 2.0).toInt())
                return 2
            if (kalah0Jewels >= floor(jewelsCount / 2.0).toInt())
                return 0
            if (kalah1Jewels >= floor(jewelsCount / 2.0).toInt())
                return 1

            return -1
        }

        private fun move(pits: MutableList<Int>, choosePitId: Int, side: Int): Int {
//          * Define size
            val pitsCount = pits.size
//          * Make move
            var jewelsFromChosenPit = pits[choosePitId]
            pits[choosePitId] = 0
            var id: Int = choosePitId
            while (jewelsFromChosenPit > 0) {
                id++
                if (shouldContinue(id, side, pitsCount)) {
                    continue
                }
                id %= pits.size
                pits[id] += 1
                jewelsFromChosenPit--
            }
//          * Catching stones
            if (pits[id] == 1 && id != getKalahId(pitsCount, side)) {
                catchStones(pits, id, getKalahId(pitsCount, side))
            }
            return id
        }

        private fun catchStones(pits: MutableList<Int>, lastPitId: Int, kalahId: Int) {
            var jewelsToKalah: Int = pits[lastPitId]
            val oppositePitsId = (pits.size - 2) - lastPitId
            if (pits[oppositePitsId] == 0)
                return

            pits[lastPitId] = 0
            jewelsToKalah += pits[oppositePitsId]
            pits[oppositePitsId] = 0
            pits[kalahId] += jewelsToKalah
        }

        private fun moveWithList(pits: List<MutableList<Int>>, choosePitId: Int, side: Int): List<MutableList<Int>> {
            var localPits = pits.toList()
//          * Define size
            val pitsCount = localPits.size
//          * Make move
            val jewelsFromChosenPit = localPits[choosePitId].toMutableList()
            localPits[choosePitId].clear()
            var id: Int = choosePitId
            while (jewelsFromChosenPit.isNotEmpty()) {
                id++
                if (shouldContinue(id, side, pitsCount)) {
                    continue
                }

                id %= localPits.size
                localPits[id].add(jewelsFromChosenPit.last())
                jewelsFromChosenPit.removeLast()
            }
//          * Catching stones
            if (localPits[id].size == 1 && id != getKalahId(pitsCount, side)) {
                localPits = catchStonesWithList(localPits, id, getKalahId(pitsCount, side))
            }
            return localPits
        }

        private fun catchStonesWithList(pits: List<MutableList<Int>>, lastPitId: Int, kalahId: Int): List<MutableList<Int>> {
            val localPits = pits.toList()
            val jewelsToKalah = localPits[lastPitId].toMutableList()
            val oppositePitsId = (localPits.size - 2) - lastPitId
            if (localPits[oppositePitsId].size == 0)
                return localPits

            jewelsToKalah.addAll(localPits[oppositePitsId])

            localPits[lastPitId].clear()
            localPits[oppositePitsId].clear()

            localPits[kalahId].addAll(jewelsToKalah)

            return localPits
        }

        private fun shouldContinue(id: Int, side: Int, pitsCount: Int): Boolean {
            return ((id == getKalahId(pitsCount, 1) && side == 0)
                    || (id == getKalahId(pitsCount, 0) && side == 1))
        }
    }
}

