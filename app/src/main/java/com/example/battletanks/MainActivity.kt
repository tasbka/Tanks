package com.example.battletanks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.KeyEvent.*
import android.view.Menu
import android.view.MenuItem
import android.view.View.*
import com.example.battletanks.enums.Direction.DOWN
import com.example.battletanks.enums.Direction.UP
import com.example.battletanks.enums.Direction.LEFT
import com.example.battletanks.enums.Direction.RIGHT
import com.example.battletanks.databinding.ActivityMainBinding
import com.example.battletanks.drawers.*
import com.example.battletanks.enums.Material

const val CELL_SIZE = 50

lateinit var  binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var editMode = false
    private val gridDrawer by lazy {
        GridDrawer(binding.container)
    }

    private  val elementDrawer by lazy {
        ElementsDrawer(binding.container)
    }

    private val tankDrawer by lazy{
        TankDrawer(binding.container)
    }

    private val bulletDrawer by lazy{
        BulletDrawer(binding.container)
    }

    private val levelStorage by lazy {
        LevelStorage(this)
    }

    private val enemyDrawer by lazy{
        EnemyDrawer(binding.container)
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
        binding.editorEagle.setOnClickListener { elementDrawer.currentMaterial = Material.EAGLE }
        binding.container.setOnTouchListener { _, event ->
            elementDrawer.onTouchContainer(event.x, event.y)
            return@setOnTouchListener true
        }
        elementDrawer.drawElementsList(levelStorage.loadLevel())
        hideSettings()
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
        binding.materialsContainer.visibility = GONE
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

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when(keyCode)
        {
            KEYCODE_DPAD_UP -> tankDrawer.move(
                binding.myTank,
                UP,
                elementDrawer.elementsOnContainer
            )
            KEYCODE_DPAD_DOWN -> tankDrawer.move(
                binding.myTank,
                DOWN,
                elementDrawer.elementsOnContainer
            )
            KEYCODE_DPAD_LEFT -> tankDrawer.move(
                binding.myTank,
                LEFT,
                elementDrawer.elementsOnContainer
            )
            KEYCODE_DPAD_RIGHT -> tankDrawer.move(
                binding.myTank,
                RIGHT,
                elementDrawer.elementsOnContainer
            )

            KEYCODE_SPACE -> bulletDrawer.makeBulletMove(
                binding.myTank,
                tankDrawer.currentDirection,
                elementDrawer.elementsOnContainer
            )
        }
        return super.onKeyDown(keyCode, event)
    }


}

