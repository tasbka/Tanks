package com.example.battletanks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_DPAD_DOWN
import android.view.KeyEvent.KEYCODE_DPAD_LEFT
import android.view.KeyEvent.KEYCODE_DPAD_RIGHT
import android.view.KeyEvent.KEYCODE_DPAD_UP
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
import com.example.battletanks.drawers.ElementsDrawer
import com.example.battletanks.drawers.GridDrawer
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
            KEYCODE_DPAD_UP -> elementDrawer.move(binding.myTank, UP)
            KEYCODE_DPAD_DOWN -> elementDrawer.move(binding.myTank, DOWN)
            KEYCODE_DPAD_LEFT -> elementDrawer.move(binding.myTank, LEFT)
            KEYCODE_DPAD_RIGHT -> elementDrawer.move(binding.myTank, RIGHT)
        }
        return super.onKeyDown(keyCode, event)
    }


}

