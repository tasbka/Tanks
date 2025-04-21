package com.example.battletanks.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.battletanks.databinding.ActivityScoreBinding
import com.example.battletanks.sounds.ScoreSoundPlayer

const val SCORE_REQUEST_CODE =100

class ScoreActivity: AppCompatActivity() {

    companion object{
        const val  EXTRA_SCORE = "extra_score"


        fun createIntent(context: Context, score: Int): Intent{
            return Intent(context,ScoreActivity::class.java)
                .apply {
                    putExtra(EXTRA_SCORE,score)
                }
        }
    }
    private val scoreSoundPlayer by lazy{
        ScoreSoundPlayer(this){
            startScoreCounting()
        }

    }

    private fun startScoreCounting() {
        Thread(Runnable {
            var currentScore = 0
            while (currentScore <= score)
            {
                runOnUiThread{
                    binding.scoreTextView.text = currentScore.toString()
                    currentScore += 100
                }
                Thread.sleep(150)
            }
            scoreSoundPlayer.pauseScoreSound()
        }).start()
    }

    var score = 0
    lateinit var binding: ActivityScoreBinding

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding =   ActivityScoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        score = intent.getIntExtra(EXTRA_SCORE,0)
        scoreSoundPlayer.playScoureSound()
    }

    override fun onPause(){
        super.onPause()
        scoreSoundPlayer.pauseScoreSound()
    }

    override fun onBackPressed(){
        super.onBackPressed()
        setResult(Activity.RESULT_OK)
        finish()
    }

}