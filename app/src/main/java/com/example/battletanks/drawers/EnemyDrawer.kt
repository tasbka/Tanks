package com.example.battletanks.drawers

import android.widget.FrameLayout
import com.example.battletanks.CELL_SIZE
import com.example.battletanks.binding
import com.example.battletanks.enums.CELLS_TANKS_SIZE
import com.example.battletanks.enums.Direction
import com.example.battletanks.enums.Material
import com.example.battletanks.models.Coordinate
import com.example.battletanks.models.Element
import com.example.battletanks.models.Tank
import com.example.battletanks.utils.drawElement

private  const val MAX_ENEMY_AMOUNT = 20

class EnemyDrawer (private  val container: FrameLayout, private val elements: MutableList<Element>) {
    private  val  respawnList: List<Coordinate>
    private var enemyAmount = 0
    private var currentCoordinate:Coordinate
    private val tanks = mutableListOf<Tank>()

    init {
        respawnList = getRespawnList()
        currentCoordinate = respawnList[0]
    }
    private fun getRespawnList(): List<Coordinate> {
        val respawnList = mutableListOf<Coordinate>()
        respawnList.add(Coordinate(0,0))
        respawnList.add(
            Coordinate(
                0,
                ((container.width - container.width % CELL_SIZE) / CELL_SIZE -
                        (container.width - container.width % CELL_SIZE) / CELL_SIZE % 2) *
                        CELL_SIZE / 2 - CELL_SIZE * CELLS_TANKS_SIZE
            )
        )
        respawnList.add(
            Coordinate(
                0,
                (container.width - container.width % CELL_SIZE) - CELL_SIZE * CELLS_TANKS_SIZE
            )
        )
        return respawnList
    }

    private fun drawEnemy() {
        var index = respawnList.indexOf(currentCoordinate) + 1
        if (index == respawnList.size) {
            index = 0
        }


        currentCoordinate = respawnList[index]
        val  enemyTank =Tank(
            Element (
            material = Material.ENEMY_TANK,
            coordinate = currentCoordinate,
            ), Direction.DOWN,
        BulletDrawer(container)
        )
        enemyTank.element.drawElement(container)
        elements.add(enemyTank.element)
        tanks.add(enemyTank)
    }

    fun moveEnemyTanks()
    {
        Thread(Runnable {
            while (true)
            {
                removeInconsistentTanks()
                tanks.forEach {
                    it.move(it.direction, container, elements)
                    it.bulletDrawer.makeBulletMove()
                }
                Thread.sleep(400)
            }
        }).start()
    }

    fun startEnemyCreation() {
        Thread(Runnable {
            while (enemyAmount < MAX_ENEMY_AMOUNT) {
                drawEnemy()
                enemyAmount++
                Thread.sleep(3000)
            }
        }).start()
    }


    //методы, которыее удаляют из массива все уничтоженные танки
    private fun removeInconsistentTanks()
    {
        tanks.removeAll(getInconsistentTanks())
    }

    private fun getInconsistentTanks(): List<Tank>
    {
        val removingTanks = mutableListOf<Tank>()
        val allTanksElements = elements.filter { it.material == Material.ENEMY_TANK }
        tanks.forEach {
            if (!allTanksElements.contains(it.element)) {
                removingTanks.add(it)
            }
        }
        return removingTanks
    }
}