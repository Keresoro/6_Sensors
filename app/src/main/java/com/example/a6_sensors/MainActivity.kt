package com.example.a6_sensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*




class MainActivity : AppCompatActivity(), SensorEventListener {

    lateinit var accel: Sensor
    lateinit var magField: Sensor

    var accelValues = FloatArray(3)
    var magFieldValues = FloatArray(3)
    var orientations = FloatArray(3)
    var orientationMatrix = FloatArray (16)
    var inclinationMatrix = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val sMgr = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accel = sMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        magField = sMgr.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

        sMgr.registerListener(this, accel, SensorManager.SENSOR_DELAY_UI)
        sMgr.registerListener(this, magField, SensorManager.SENSOR_DELAY_UI)
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {

    }

    override fun onSensorChanged(ev: SensorEvent) {
        // Test which sensor has been detected.

        if (ev.sensor == accel) {
            val xyz = ev.values
            XAxis.setText(xyz[0].toString())
            YAxis.setText(xyz[1].toString())
            ZAxis.setText(xyz[2].toString())

            accelValues = ev.values.clone()
        }
        else if (ev.sensor == magField) {
            magFieldValues = ev.values.clone()
        }
        SensorManager.getRotationMatrix (orientationMatrix, inclinationMatrix, accelValues, magFieldValues)
        SensorManager.getOrientation (orientationMatrix, orientations)
        azimuth.setText(orientations[0].toString())
        pitch.setText(orientations[1].toString())
        roll.setText(orientations[2].toString())

    }
}


