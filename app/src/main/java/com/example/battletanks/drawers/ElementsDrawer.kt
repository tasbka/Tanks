package com.example.battletanks.drawers

import android.view.View
import android.widget.FrameLayout
import  android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.view.marginLeft
import androidx.core.view.marginTop
import  com.example.battletanks.CELL_SIZE
import com.example.battletanks.R
import com.example.battletanks.binding
import com.example.battletanks.enums.Direction
import  com.example.battletanks.enums.Material
import com.example.battletanks.models.Coordinate
import com.example.battletanks.models.Element
import com.example.battletanks.utils.getElementByCoordinates


class ElementsDrawer(val container: FrameLayout) {
    var currentMaterial = Material.EMPTY
    val elementsOnContainer = mutableListOf<Element>()

    fun onTouchContainer(x: Float, y: Float) {
        val topMargin = y.toInt() - (y.toInt() % CELL_SIZE)
        val leftMargin = x.toInt() - (x.toInt() % CELL_SIZE)
        val coordinate = Coordinate(topMargin, leftMargin)
        if (currentMaterial == Material.EMPTY)
        {eraseView(coordinate)}
        else{
            drawOrReplaceView(coordinate)
        }

    }

    private fun drawOrReplaceView(coordinate:Coordinate)
    {
        val viewOnCoordinate = getElementByCoordinates(coordinate,elementsOnContainer)
        if (viewOnCoordinate==null){
            drawView(coordinate)
            return
        }
        if (viewOnCoordinate.material !=currentMaterial){
            replaceView(coordinate)
        }
    }

    private fun replaceView(coordinate: Coordinate){
        eraseView(coordinate)
        drawView(coordinate)
    }



    private fun eraseView(coordinate: Coordinate){
        val elementOnCoordinate = getElementByCoordinates(coordinate,elementsOnContainer)
        if (elementOnCoordinate!=null)
        {
            val erasingView = container.findViewById<View>(elementOnCoordinate.viewId)
            container.removeView(erasingView)
            elementsOnContainer.remove(elementOnCoordinate)
        }
    }

    fun selectMaterial(coordinate: Coordinate) {
        when (currentMaterial) {
            Material.BRICK -> drawView(R.drawable.brick, coordinate)
            Material.CONCRETE -> drawView(R.drawable.concrete, coordinate)
            Material.GRASS -> drawView(R.drawable.grass, coordinate)
            Material.EAGLE -> {
                removeExistingEagle()
                drawView(R.drawable.eagle, coordinate, 4, 3)
            }
            Material.EMPTY -> {}
        }
    }

    fun drawView(coordinate: Coordinate) {
        val view = ImageView(container.context)
        val layoutParams = FrameLayout.LayoutParams(CELL_SIZE, CELL_SIZE)
        when (currentMaterial) {
            Material.EMPTY -> {


            }

            Material.BRICK -> view.setImageResource(R.drawable.brick)
            Material.CONCRETE -> view.setImageResource(R.drawable.concrete)
            Material.GRASS -> view.setImageResource(R.drawable.grass)
        }
        layoutParams.topMargin = coordinate.top
        layoutParams.leftMargin = coordinate.left
        val viewId = View.generateViewId()
        view.id = viewId
        view.layoutParams = layoutParams
        container.addView(view)
        elementsOnContainer.add(Element(viewId, currentMaterial, coordinate))
    }
}
    private fun removeExistingEagle() {
    elementOnContainer.firstOrNull {it.material == Material.EAGLE}?.coordinate?.let {
        eraseView(it)
        }//НАДО РАЗОБРАТЬСЯ ПРЕЗА 11 СЛАЙД 5
    }
    private fun drawVieww(
        @DrawableRes image: Int,
        coordinate: Coordinate,
        width: Int = 1,
        height: Int =1
    ) {
        val view = ImageView(container.context)
        val layoutParams = FrameLayout.LayoutParams(width * CELL_SIZE, height * CELL_SIZE )
        view.setImageResource(image)
        layoutParams.topMargin = coordinate.top
        layoutParams.leftMargin = coordinate.left
        val viewId = View.generateViewId()
        view.id = viewId
        view.layoutParams = layoutParams
        container.addView(view)
        elementsOnContainer.add(Element(viewId, currentMaterial, coordinate, width, height))
    }
}