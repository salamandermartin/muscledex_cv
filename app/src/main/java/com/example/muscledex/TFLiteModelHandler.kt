package com.example.muscledex

import android.content.Context
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class TFLiteModelHandler(private val context: Context) {
    private lateinit var interpreter: Interpreter

    init {
        // Load the model when the class is instantiated
        interpreter = Interpreter(loadModelFile("movenet_model.tflite"))
    }

    // Function to load the TensorFlow Lite model from the assets folder
    private fun loadModelFile(modelPath: String): MappedByteBuffer {
        val fileDescriptor = context.assets.openFd(modelPath)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    // Function to run inference on the input data
    fun runInference(inputData: Array<FloatArray>): Array<FloatArray> {
        val outputData = Array(1) { FloatArray(17 * 3) } // Assuming 17 keypoints with 3 values (x, y, confidence)
        interpreter.run(inputData, outputData)
        return outputData
    }

    // Clean up the interpreter resources when done
    fun close() {
        interpreter.close()
    }
}