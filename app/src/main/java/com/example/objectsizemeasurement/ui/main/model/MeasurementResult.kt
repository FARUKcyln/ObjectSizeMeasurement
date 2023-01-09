package com.example.objectsizemeasurement.ui.main.model

import com.google.mlkit.vision.objects.DetectedObject

data class MeasurementResult(
    var detectedObject: DetectedObject,
    var detectedObjectDimensions: ArrayList<Float>,
    var captureNumber: Int
)
