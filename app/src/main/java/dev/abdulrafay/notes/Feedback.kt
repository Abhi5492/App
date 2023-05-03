package dev.abdulrafay.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RatingBar
import android.widget.Toast
import kotlin.math.exp

class Feedback : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)

        val rBar1 = findViewById<RatingBar>(R.id.ratingbar)
        val rBar2 = findViewById<RatingBar>(R.id.experience)
        val rBar3 = findViewById<RatingBar>(R.id.easytouse)
        val rBar4 = findViewById<RatingBar>(R.id.ecofriendly)
        val rBar5 = findViewById<RatingBar>(R.id.intutive)

        if (rBar1 != null &&rBar2 != null && rBar3 != null && rBar4 != null && rBar5 != null  ) {
            val button = findViewById<Button>(R.id.btnsubmit)
            button?.setOnClickListener {
                val msg = rBar1.rating.toString().toFloat()
                val experience = rBar2.rating.toString().toFloat()
                val easytouse =rBar3.rating.toString().toFloat()
                val ecofriendly= rBar4.rating.toString().toFloat()
                val intutive = rBar5.rating.toString().toFloat()

                val average = (msg+ experience+easytouse+ecofriendly+intutive)/5;
                Toast.makeText(this@Feedback,
                    " Average Rating is: "+average.toString(), Toast.LENGTH_SHORT).show()
            }
        }
        else{
            Toast.makeText(this,"Please rate all the fields ",Toast.LENGTH_SHORT).show()
        }
    }
}