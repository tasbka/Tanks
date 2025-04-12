package com.example.battletanks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.KeyEvent.*
import android.view.Menu
import android.view.MenuItem
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.View.*
import android.view.ViewTreeObserver
import com.example.battletanks.enums.Direction.DOWN
import com.example.battletanks.enums.Direction.UP
import com.example.battletanks.enums.Direction.LEFT
import com.example.battletanks.enums.Direction.RIGHT
import com.example.battletanks.databinding.ActivityMainBinding
import com.example.battletanks.drawers.*
import com.example.battletanks.enums.Direction
import com.example.battletanks.enums.Material
import com.example.battletanks.models.Coordinate
import com.example.battletanks.models.Element
import com.example.battletanks.models.Tank

const val CELL_SIZE = 50

lateinit var  binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var editMode = false
    private lateinit var playerTank: Tank
    private lateinit var eagle: Element
    private fun createTank(elementWidth: Int, elementHeight: Int): Tank {
        playerTank = Tank (
            Element(
                material = Material.PLAYER_TANK,
                coordinate = getPlayerTankCoordinate(elementWidth, elementHeight)
            ), UP,
            BulletDrawer(binding.container, elementDrawer.elementsOnContainer)
        )
        return playerTank
    }
    private fun createEagle (elementWidth: Int, elementHeight: Int):Element
    {
        eagle = Element(
            material = Material.EAGLE,
            coordinate = getEagleCoordinate(elementWidth,elementHeight)
        )
        return eagle
    }
    private fun getPlayerTankCoordinate(width: Int, height: Int) = Coordinate (
        top = (height - height%2)
    - (height - height%2)% CELL_SIZE
- Material.PLAYER_TANK.height* CELL_SIZE,
        left = (width - width%(2* CELL_SIZE))/2
    - Material.EAGLE.width/2* CELL_SIZE
    - Material.PLAYER_TANK.width/2* CELL_SIZE
    )
    private fun getEagleCoordinate(width: Int,height: Int) = Coordinate (
        top = (height - height%2)
                - (height - height%2)% CELL_SIZE
                - Material.EAGLE.height* CELL_SIZE,
        left = (width - width%(2* CELL_SIZE))/2
                - Material.EAGLE.width/2* CELL_SIZE
            )
    private val gridDrawer by lazy {
        GridDrawer(binding.container)
    }

    private  val elementDrawer by lazy {
        ElementsDrawer(binding.container)
    }

    private val levelStorage by lazy {
        LevelStorage(this)
    }
    private val enemyDrawer by lazy {
        EnemyDrawer(binding.container, elementDrawer.elementsOnContainer)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Menu"

        binding.editorClear.setOnClickListener { elementDrawer.currentMaterial = Material.EMPTY }
        binding.editorBrick.setOnClickListener { elementDrawer.currentMaterial = Material.BRICK }
        binding.editorConcrete.setOnClickListener {
            elementDrawer.currentMaterial = Material.CONCRETE
        }
        binding.editorGrass.setOnClickListener { elementDrawer.currentMaterial = Material.GRASS }
        binding.container.setOnTouchListener { _, event ->
            if (!editMode){
                return@setOnTouchListener true
            }
            elementDrawer.onTouchContainer(event.x, event.y)
            return@setOnTouchListener true
        }
        elementDrawer.drawElementsList(levelStorage.loadLevel())
        /*elementDrawer.drawElementsList(listOf(playerTank.element, eagle))*/
        hideSettings()
        countWidthHeight()
    }
    private fun countWidthHeight() {
        val frameLayout = binding.container
        frameLayout.viewTreeObserver
            .addOnGlobalLayoutListener (object : OnGlobalLayoutListener {
                override fun onGlobalLayout(){
                    frameLayout.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    val elementWidth = frameLayout.width
                    val elementHeight = frameLayout.height

                    playerTank = createTank(elementWidth, elementHeight)
                    eagle = createEagle(elementWidth, elementHeight)

                    elementDrawer.drawElementsList(listOf(playerTank.element,eagle))
                }
            })
    }
    private fun switchEditMode()
    {
        if (editMode)
        {
            showSettings()

        } else {
            hideSettings()
        }
        editMode = !editMode
    }

    private fun showSettings ()
    {
        gridDrawer.drawGrid()
        binding.materialsContainer.visibility = VISIBLE
    }

    private fun hideSettings()
    {
        gridDrawer.removeGrid()
        binding.materialsContainer.visibility = INVISIBLE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean
    {
        menuInflater.inflate(R.menu.settings, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        return when(item.itemId) {
            R.id.menu_settings -> {
                switchEditMode()
                return true
            }
            R.id.menu_save -> {
                levelStorage.saveLevel(elementDrawer.elementsOnContainer)
                return true
            }
            R.id.menu_play -> {
                startTheGame()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun startTheGame(){
        if (editMode){
            return
        }
        enemyDrawer.startEnemyCreation()
        enemyDrawer.moveEnemyTanks()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when(keyCode)
        {
            KEYCODE_DPAD_UP -> move(
                UP,
            )
            KEYCODE_DPAD_DOWN -> move(
                DOWN,
            )
            KEYCODE_DPAD_LEFT -> move(
                LEFT,
            )
            KEYCODE_DPAD_RIGHT -> move(
                RIGHT,
            )

            KEYCODE_SPACE -> playerTank.bulletDrawer.makeBulletMove(playerTank)
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun move (direction:Direction)
    {
        playerTank.move(direction, binding.container,elementDrawer.elementsOnContainer)
    }

}

