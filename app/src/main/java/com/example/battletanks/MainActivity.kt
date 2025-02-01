package com.example.battletanks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.KeyEvent.*
import android.view.Menu
import android.view.MenuItem
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.FrameLayout
import androidx.core.view.marginLeft
import androidx.core.view.marginTop
import com.example.battletanks.enums.Direction.DOWN
import com.example.battletanks.enums.Direction.UP
import com.example.battletanks.enums.Direction.LEFT
import com.example.battletanks.enums.Direction.RIGHT
import com.example.battletanks.databinding.ActivityMainBinding
import com.example.battletanks.drawers.BulletDrawer
import com.example.battletanks.drawers.ElementsDrawer
import com.example.battletanks.drawers.GridDrawer
import com.example.battletanks.drawers.TankDrawer
import com.example.battletanks.enums.Direction
import com.example.battletanks.enums.Material
import com.example.battletanks.models.Coordinate

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
            elementDrawer.onTouchContainer(event.x, event.y)
            return@setOnTouchListener true
        }
    }

    private fun switchEditMode()
    {
        if (editMode)
        {
            gridDrawer.removeGrid()
            binding.materialsContainer.visibility = INVISIBLE

        } else {
            gridDrawer.drawGrid()
            binding.materialsContainer.visibility = VISIBLE
        }
        editMode = !editMode
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

            KEYCODE_SPACE -> bulletDrawer.drawBullet(
                binding.myTank,
                tankDrawer.currentDirection
            )
        }
        return super.onKeyDown(keyCode, event)
    }


}

