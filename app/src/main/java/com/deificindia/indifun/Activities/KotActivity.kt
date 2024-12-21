package com.deificindia.indifun.Activities

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.deificindia.indifun.R
import com.deificindia.indifun.dialogs.DailyCheckinDialog
import java.util.*

class KotActivity : AppCompatActivity() {

  
    var button: Button? = null
    var textView: TextView? = null
    var wheelRoul: ImageView? = null


    lateinit var r: Random
    var degree = 0
    var degree_old:Int = 0

    //his was 37 but i had an extra zero
    //becau[se there is 38 sectors on the wheel (9.47 degrees each)
    val FACTOR = 4.7368f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rolett_layout)

        button =  findViewById(R.id.btn_spin);
        textView =  findViewById(R.id.textView);
        wheelRoul = findViewById(R.id.imRoulette);

        r = Random()

        button?.setOnClickListener(View.OnClickListener {
            degree_old = degree % 360
            degree = r.nextInt(360) + 720
            val rotate = RotateAnimation(degree_old.toFloat(), degree.toFloat(),
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f)
            rotate.duration = 3600
            rotate.fillAfter = true
            rotate.interpolator = DecelerateInterpolator()
            rotate.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {
                    textView?.setText("")
                }

                override fun onAnimationEnd(animation: Animation) {
                    textView?.setText(currentNumber(360 - degree % 360))
                }

                override fun onAnimationRepeat(animation: Animation) {}
            })
            wheelRoul?.startAnimation(rotate)
        })
    }

    private fun currentNumber(degrees: Int): String? {
        var text = ""
        if (degrees >= FACTOR * 1 && degrees < FACTOR * 3) {
            text = "27 red"
        }
        if (degrees >= FACTOR * 3 && degrees < FACTOR * 5) {
            text = "10 black"
        }
        if (degrees >= FACTOR * 5 && degrees < FACTOR * 7) {
            text = "35 red"
        }
        if (degrees >= FACTOR * 7 && degrees < FACTOR * 9) {
            text = "29 black"
        }
        if (degrees >= FACTOR * 9 && degrees < FACTOR * 11) {
            text = "12 red"
        }
        if (degrees >= FACTOR * 11 && degrees < FACTOR * 13) {
            text = "8 black"
        }
        if (degrees >= FACTOR * 13 && degrees < FACTOR * 15) {
            text = "19 red"
        }
        if (degrees >= FACTOR * 15 && degrees < FACTOR * 17) {
            text = "31 black"
        }
        if (degrees >= FACTOR * 17 && degrees < FACTOR * 19) {
            text = "18 red"
        }
        if (degrees >= FACTOR * 19 && degrees < FACTOR * 21) {
            text = "6 black"
        }
        if (degrees >= FACTOR * 21 && degrees < FACTOR * 23) {
            text = "21 red"
        }
        if (degrees >= FACTOR * 23 && degrees < FACTOR * 25) {
            text = "33 black"
        }
        if (degrees >= FACTOR * 25 && degrees < FACTOR * 27) {
            text = "16 red"
        }
        if (degrees >= FACTOR * 27 && degrees < FACTOR * 29) {
            text = "4 black"
        }
        if (degrees >= FACTOR * 29 && degrees < FACTOR * 31) {
            text = "23 red"
        }
        if (degrees >= FACTOR * 31 && degrees < FACTOR * 33) {
            text = "35 black"
        }
        if (degrees >= FACTOR * 33 && degrees < FACTOR * 35) {
            text = "14 red"
        }
        if (degrees >= FACTOR * 35 && degrees < FACTOR * 37) {
            text = "2 black"
        }
        if (degrees >= FACTOR * 37 && degrees < FACTOR * 39) {
            text = "zero"
        }
        if (degrees >= FACTOR * 39 && degrees < FACTOR * 41) {
            text = "28 black"
        }
        if (degrees >= FACTOR * 41 && degrees < FACTOR * 43) {
            text = "9 red"
        }
        if (degrees >= FACTOR * 43 && degrees < FACTOR * 45) {
            text = "26 black"
        }
        if (degrees >= FACTOR * 45 && degrees < FACTOR * 47) {
            text = "30 red"
        }
        if (degrees >= FACTOR * 47 && degrees < FACTOR * 49) {
            text = "11 black"
        }
        if (degrees >= FACTOR * 49 && degrees < FACTOR * 51) {
            text = "7 red"
        }
        if (degrees >= FACTOR * 51 && degrees < FACTOR * 53) {
            text = "20 black"
        }
        if (degrees >= FACTOR * 53 && degrees < FACTOR * 55) {
            text = "32 red"
        }
        if (degrees >= FACTOR * 55 && degrees < FACTOR * 57) {
            text = "17 black"
        }
        if (degrees >= FACTOR * 57 && degrees < FACTOR * 59) {
            text = "5 red"
        }
        if (degrees >= FACTOR * 59 && degrees < FACTOR * 61) {
            text = "22 black"
        }
        if (degrees >= FACTOR * 61 && degrees < FACTOR * 63) {
            text = "34 red"
        }
        if (degrees >= FACTOR * 63 && degrees < FACTOR * 65) {
            text = "15 black"
        }
        if (degrees >= FACTOR * 65 && degrees < FACTOR * 67) {
            text = "3 red"
        }
        if (degrees >= FACTOR * 67 && degrees < FACTOR * 69) {
            text = "24 black"
        }
        if (degrees >= FACTOR * 69 && degrees < FACTOR * 71) {
            text = "36 red"
        }
        if (degrees >= FACTOR * 71 && degrees < FACTOR * 73) {
            text = "13 black"
        }
        if (degrees >= FACTOR * 73 && degrees < FACTOR * 75) {
            text = "1 red"
        }
        if (degrees >= FACTOR * 75 && degrees < 360 || degrees >= 0 && degrees < FACTOR * 77) {
            text = "zero"
        }
        return text
    }
}