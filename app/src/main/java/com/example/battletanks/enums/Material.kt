package com.example.battletanks.enums

import android.content.SharedPreferences.Editor
import com.example.battletanks.R

const val CELL_SIMPLE_ELEMENT = 1
const val CELLS_EAGLE_WIDTH = 4
const val CELLS_EAGLE_HEIGHT = 3
const val CELLS_TANKS_SIZE = 2

enum class Material(
    val tankConGoThrough: Boolean,
    val bulletCanGoThrough:Boolean,
    val simpleBulletCanDestroy: Boolean,
    val elementsAboutOnScreen: Int,
    val width: Int,
    val height: Int,
    val image: Int,
    val visibleInEditableMode: Boolean
    ) {
        EMPTY(true,true,true, 0,0,0,0,false),
    BRICK(false,false,true,0, CELL_SIMPLE_ELEMENT,CELL_SIMPLE_ELEMENT, R.drawable.brick,false),
    CONCRETE(false,false,false,0,CELL_SIMPLE_ELEMENT,CELL_SIMPLE_ELEMENT,R.drawable.concrete,false),
    GRASS(true,true,false, 0,CELL_SIMPLE_ELEMENT,CELL_SIMPLE_ELEMENT, R.drawable.grass,false),
    EAGLE(false,false,true,1,CELLS_EAGLE_WIDTH,CELLS_EAGLE_HEIGHT,R.drawable.eagle,false),
    ENEMY_TANK_RESPAWN(true,true,false,3,CELLS_TANKS_SIZE, CELLS_TANKS_SIZE,R.drawable.enemy_tank,true),
    PLAYER_TANK_RESPAWN(true,true,false,1,CELLS_TANKS_SIZE, CELLS_TANKS_SIZE,R.drawable.tank,true)
}

