package com.example.tictactoe

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var activePlayer = 1 // 1 for X, 2 for O
    private var gameState = IntArray(9) { 0 } // 0 - empty, 1 - X, 2 - O
    private val winningPositions = arrayOf(
        intArrayOf(0, 1, 2),
        intArrayOf(3, 4, 5),
        intArrayOf(6, 7, 8),
        intArrayOf(0, 3, 6),
        intArrayOf(1, 4, 7),
        intArrayOf(2, 5, 8),
        intArrayOf(0, 4, 8),
        intArrayOf(2, 4, 6)
    )
    private var gameActive = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val statusTextView = findViewById<TextView>(R.id.statusTextView)
        val restartButton = findViewById<Button>(R.id.restartButton)
        val gridLayout = findViewById<GridLayout>(R.id.gridLayout)

        // Initialize buttons and set onClick listeners
        for (i in 0 until gridLayout.childCount) {
            val button = gridLayout.getChildAt(i) as Button
            button.setOnClickListener {
                onButtonClick(button, i, statusTextView, restartButton)
            }
        }

        restartButton.setOnClickListener {
            restartGame(gridLayout, statusTextView, restartButton)
        }
    }

    private fun onButtonClick(
        button: Button,
        position: Int,
        statusTextView: TextView,
        restartButton: Button
    ) {
        if (gameActive && gameState[position] == 0) {
            gameState[position] = activePlayer
            button.text = if (activePlayer == 1) "X" else "O"
            button.isEnabled = false

            if (checkWinner()) {
                gameActive = false
                val winner = if (activePlayer == 1) "X" else "O"
                statusTextView.text = getString(R.string.player_wins, winner)
                restartButton.visibility = View.VISIBLE
            } else if (!gameState.contains(0)) {
                statusTextView.text = getString(R.string.it_s_a_draw)
                restartButton.visibility = View.VISIBLE
            } else {
                activePlayer = if (activePlayer == 1) 2 else 1
                statusTextView.text =
                    getString(R.string.player_s_turn, if (activePlayer == 1) "X" else "O")
            }
        }
    }

    private fun checkWinner(): Boolean {
        for (winningPosition in winningPositions) {
            if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                gameState[winningPosition[0]] != 0
            ) {
                return true
            }
        }
        return false
    }

    private fun restartGame(
        gridLayout: GridLayout,
        statusTextView: TextView,
        restartButton: Button
    ) {
        gameState = IntArray(9) { 0 }
        activePlayer = 1
        gameActive = true
        statusTextView.text = getString(R.string.player_x_s_turnn)
        restartButton.visibility = View.GONE

        for (i in 0 until gridLayout.childCount) {
            val button = gridLayout.getChildAt(i) as Button
            button.text = ""
            button.isEnabled = true
        }
    }
}
