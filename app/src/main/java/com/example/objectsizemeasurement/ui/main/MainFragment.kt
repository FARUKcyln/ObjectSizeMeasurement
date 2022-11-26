package com.example.objectsizemeasurement.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Point
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.util.Size
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.objectsizemeasurement.R
import com.example.objectsizemeasurement.databinding.FragmentMainBinding
import com.example.objectsizemeasurement.utils.Draw
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.common.model.LocalModel
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.objects.ObjectDetection
import com.google.mlkit.vision.objects.ObjectDetector
import com.google.mlkit.vision.objects.custom.CustomObjectDetectorOptions
import org.opencv.android.OpenCVLoader

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var objectDetector: ObjectDetector
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        if (!allPermissionGranted()) {
            ActivityCompat.requestPermissions(
                requireActivity(), Constants.REQUIRED_PERMISSION, Constants.REQUEST_CODE_PERMISSION
            )
        }


        if (OpenCVLoader.initDebug()) {
            Log.d("LOADED", "success")
        } else {
            Log.d("LOADED", "error")
        }

        cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({

            val cameraProvider = cameraProviderFuture.get()
            bindPreview(cameraProvider = cameraProvider)

        }, ContextCompat.getMainExecutor(requireContext()))

        val localModel = LocalModel.Builder().setAssetFilePath("object_detection.tflite").build()

        val customObjectDetectorOptions = CustomObjectDetectorOptions.Builder(localModel)
            .setDetectorMode(CustomObjectDetectorOptions.STREAM_MODE).enableClassification()
            .setClassificationConfidenceThreshold(0.7f).setMaxPerObjectLabelCount(10).build()

        objectDetector = ObjectDetection.getClient(customObjectDetectorOptions)

    }

    @SuppressLint("UnsafeOptInUsageError")
    private fun bindPreview(cameraProvider: ProcessCameraProvider) {

        val preview = Preview.Builder().build()
        val cameraSelector =
            CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()

        preview.setSurfaceProvider(binding.previewView.surfaceProvider)

        val display = requireActivity().windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)

        val imageAnalysis = ImageAnalysis.Builder().setTargetResolution(Size(size.x, size.y))
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST).build()

        imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(requireContext())) { imageProxy ->
            val rotationDegrees = imageProxy.imageInfo.rotationDegrees
            val image = imageProxy.image

            if (image != null) {

                val processImage = InputImage.fromMediaImage(image, rotationDegrees)

                objectDetector.process(processImage).addOnSuccessListener { objects ->

                    for (i in objects) {

                        if (binding.main.childCount > 1) binding.main.removeViewAt(1)

                        var element: Draw? = null

                        if (isAdded) {
                            element = Draw(
                                requireContext(),
                                i.boundingBox,
                                i.labels.firstOrNull()?.text ?: "Undefined"
                            )
                        }
                        if (element != null) {
                            binding.main.addView(element)
                        }
                    }
                    imageProxy.close()
                }.addOnFailureListener { error ->
                    Log.v("MainFragment", "Error - ${error.message}")
                    imageProxy.close()
                }
            }
        }

        cameraProvider.bindToLifecycle(this, cameraSelector, imageAnalysis, preview)
    }

    private fun allPermissionGranted(): Boolean {
        return Constants.REQUIRED_PERMISSION.all {
            ContextCompat.checkSelfPermission(
                requireContext(), it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        if (requestCode == Constants.REQUEST_CODE_PERMISSION) {
            if (allPermissionGranted()) {
                //no-op
            } else {
                android.R.attr.finishOnTaskLaunch
            }
        }
    }

}

object Constants {
    const val TAG = "cameraX"
    const val FILE_NAME_FORMAT = "yy-MM-dd-HH-mm-ss-SSS"
    const val REQUEST_CODE_PERMISSION = 123
    val REQUIRED_PERMISSION = arrayOf(Manifest.permission.CAMERA)
}