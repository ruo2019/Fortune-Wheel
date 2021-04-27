package com.conkermobile.luck

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.DataBindingUtil
import com.bluehomestudio.luckywheel.WheelItem
import com.conkermobile.luck.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val wheelItems: MutableList<WheelItem> = ArrayList()

        wheelItems.add(WheelItem(
            Color.RED,
            ContextCompat.getDrawable(this, R.drawable.dice_1)?.toBitmap()))

        wheelItems.add(WheelItem(Color.rgb(255,170,0),
            ContextCompat.getDrawable(this, R.drawable.dice_2)?.toBitmap()))

        wheelItems.add(WheelItem(Color.GREEN,
            ContextCompat.getDrawable(this, R.drawable.dice_3)?.toBitmap()))

        wheelItems.add(WheelItem(Color.rgb(3, 169, 252),
            ContextCompat.getDrawable(this, R.drawable.dice_4)?.toBitmap()))

        wheelItems.add(WheelItem(Color.BLUE,
            ContextCompat.getDrawable(this, R.drawable.dice_5)?.toBitmap()))

        wheelItems.add(WheelItem(Color.MAGENTA,
            ContextCompat.getDrawable(this, R.drawable.dice_6)?.toBitmap()))

        val lwv = binding.lwv
        val lwv2 = binding.lwv2
        lwv.addWheelItems(wheelItems)
        lwv2.addWheelItems(wheelItems)

        val winner = binding.winner
        val winner2 = binding.winner2
        val tie = binding.tie
        makeInvisible(winner, winner2, tie)

        binding.button.setOnClickListener {
            binding.button.isEnabled = false
            binding.button.background = ContextCompat.getDrawable(this, R.drawable.green_rounded_corners_inactive)
            val number = (1..6).random()
            val number2 = (1..6).random()
            lwv.rotateWheelTo(number)
            lwv2.rotateWheelTo(number2)

            val score = binding.score
            score.text = number.toString()
            val score2 = binding.score2
            score2.text = number2.toString()
            makeInvisible(score, score2)

            makeInvisible(winner, winner2, tie)

            lwv.setLuckyWheelReachTheTarget {
                binding.button.isEnabled = true
                binding.button.background = ContextCompat.getDrawable(this, R.drawable.green_rounded_corners)
                makeVisible(score, score2)
                if (score.text == score2.text) {
                    makeVisible(tie)
                    makeInvisible(winner, winner2)
                } else if (score.text.toString().toInt() > score2.text.toString().toInt()) {
                    makeVisible(winner)
                    makeInvisible(tie, winner2)
                } else {
                    makeVisible(winner2)
                    makeInvisible(tie, winner)
                }

            }
        }
    }

    private fun makeInvisible(vararg views: View) {
        for (view in views) {
            view.visibility = INVISIBLE
        }
    }

    private fun makeVisible(vararg views: View) {
        for (view in views) {
            view.visibility = VISIBLE
        }
    }
}