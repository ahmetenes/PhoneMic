package com.example.phonemic

import android.content.Context
import android.graphics.drawable.LevelListDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var counter = 0
        val micButton:ImageButton = findViewById(R.id.micButton)
        val statusRing:ImageView = findViewById(R.id.status_ring)


        micButton.setOnClickListener {
            counter++
            statusRing.setImageLevel(counter%3)
        }


    }
}