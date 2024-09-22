package com.example.muscledex

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var modelHandler: TFLiteModelHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the model handler
        modelHandler = TFLiteModelHandler(this)

        // Prepare some example input data (replace with actual input)
        val inputData = Array(1) { FloatArray(224 * 224) } // Adjust according to your model input shape

        // Run the model inference
        val outputData = modelHandler.runInference(inputData)

    }

    override fun onDestroy(){
        super.onDestroy()
        // Make sure to close the interpreter to release resources
        modelHandler.close()
    }
}