package com.example.battletanks.enums

import com.example.battletanks.R

const val CELL_SIMPLE_ELEMENT = 1
const val CELLS_EAGLE_WIDTH = 4
const val CELLS_EAGLE_HEIGHT = 3

enum class Material(
    val tankConGoThrough: Boolean,
    val bulletCanGoThrough:Boolean,
    val simpleBulletCanDestroy: Boolean,
    val canExistOnlyOne: Boolean,
    val width: Int,
    val height: Int,
    val image: Int
    ) {
    EMPTY(true,true,true, false,0,0,0),
    BRICK(false,false,true,false, CELL_SIMPLE_ELEMENT,CELL_SIMPLE_ELEMENT, R.drawable.brick),
    CONCRETE(false,false,false,false,CELL_SIMPLE_ELEMENT,CELL_SIMPLE_ELEMENT,R.drawable.concrete),
    GRASS(true,true,false,false,CELL_SIMPLE_ELEMENT,CELL_SIMPLE_ELEMENT, R.drawable.grass),
    EAGLE(false,false,true,true,CELLS_EAGLE_WIDTH,CELLS_EAGLE_HEIGHT,R.drawable.eagle),
}

