package com.example.backgroundlocation

import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer

class MainActivity : AppCompatActivity() {

    lateinit var textView: TextView
    lateinit var startButton: Button
    lateinit var stopButton: Button
    private val locationHistory = mutableListOf<Location>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.textview)
        startButton = findViewById(R.id.button_start)
        stopButton = findViewById(R.id.button_stop)


        val locationSensor = LocationSensor(this)
        locationSensor.requestLocationPermission()

        stopButton.isEnabled = false

        locationSensor.location.observe(this, Observer {
            val historyText = locationHistory.joinToString("\n") { "${it.latitude}, ${it.longitude}" }
            textView.text = historyText
        })

        startButton.setOnClickListener {
            if (!locationSensor.run) {
                locationSensor.start(locationHistory)
                startButton.isEnabled = false
                stopButton.isEnabled = true
            }
        }

        stopButton.setOnClickListener {
            if (locationSensor.run) {
                locationSensor.stop()
                startButton.isEnabled = true
                stopButton.isEnabled = false
            }
        }

    }
}


