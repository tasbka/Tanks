package com.example.battletanks.utils

import com.example.battletanks.binding
import com.example.battletanks.models.Coordinate
import android.view.View
import com.example.battletanks.CELL_SIZE
import com.example.battletanks.models.Element

fun View.checkViewCanMoveThroungBorder(coordinate: Coordinate): Boolean {
    return coordinate.top >= 0 &&
            coordinate.top + this.height <= binding.container.height &&
            coordinate.left >= 0 &&
            coordinate.left + this.width <= binding.container.width
}

fun getElementByCoordinates(
        coordinate: Coordinate,
        elementsOnContainer: List<Element>
    ): Element? {

    for (element in elementsOnContainer) {
        for (height in 0 until element.height) {
            for (wight in 0 until element.width) {
                val searchingCoordinate = Coordinate(
                    top = element.coordinate.top + height * CELL_SIZE,
                    left = element.coordinate.left + wight * CELL_SIZE
                )
                if (coordinate == searchingCoordinate) {
                    return element
                }
            }
        }
    }
    return null
}