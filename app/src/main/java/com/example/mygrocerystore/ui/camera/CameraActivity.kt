package com.example.mygrocerystore.ui.camera

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.mygrocerystore.MainActivity
import com.example.mygrocerystore.R
import com.example.mygrocerystore.data.response.PredictResponse
import com.example.mygrocerystore.data.retrofit.ApiConfig
import com.example.mygrocerystore.databinding.ActivityCameraBinding
import com.example.mygrocerystore.ui.result.ResultActivity
import com.example.mygrocerystore.utils.reduceFileImage
import com.example.mygrocerystore.utils.uriToFile
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Locale

class CameraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraBinding
    private lateinit var imageCapture: ImageCapture
    private lateinit var outputDirectory: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        outputDirectory = getOutputDirectory()
        setUpAction()
        startCamera()
        startCountDown()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists()) mediaDir else filesDir
    }

    private fun startCountDown() {
        Toast.makeText(this@CameraActivity, "Scanning", Toast.LENGTH_SHORT).show()
        object : CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // No action needed on tick
            }

            override fun onFinish() {
                takePhoto()
            }
        }.start()
    }

    private fun takePhoto() {
        val photoFile = File(
            outputDirectory,
            SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(System.currentTimeMillis()) + ".jpg"
        )

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Camera Response 4: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    Log.d(TAG, "Photo capture succeeded: $savedUri")
                    validateImage(savedUri)
                }
            }
        )
    }

    private fun validateImage(uri: Uri) {
        var imageFile = uriToFile(uri, this).reduceFileImage()

        if (!imageFile.extension.equals("jpg", ignoreCase = true)) {
            imageFile = convertToJpg(imageFile)
        }

        val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "image",
            imageFile.name,
            requestImageFile
        )

        lifecycleScope.launch {
            try {
                val apiService = ApiConfig.getApiService()
                val successResponse = apiService.uploadImage(multipartBody)
                navigateToResult(uri, successResponse)
                Log.d(TAG, "Camera response 1: ${Gson().toJson(successResponse)}")
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, PredictResponse::class.java)
                Log.e(TAG, "Camera response 2: ${Gson().toJson(errorResponse)}")
                showToast(errorResponse.status ?: "An error occurred")
            } catch (e: Exception) {
                Log.e(TAG, "Unexpected error 5: ${e.message}", e)
                showToast("Unexpected error occurred")
            }
        }
    }

    private fun convertToJpg(file: File): File {
        Log.d(TAG, "Camera convertToJpg: ${file.path}")
        val bitmap = BitmapFactory.decodeFile(file.path)
        val jpgFile = File(file.parent, file.nameWithoutExtension + ".jpg")
        FileOutputStream(jpgFile).use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
        }
        return jpgFile
    }

    private fun navigateToResult(uri: Uri, predictResponse: PredictResponse) {
        val intent = Intent(this@CameraActivity, ResultActivity::class.java).apply {
            putExtra("IMAGE_URI", uri.toString())
            putExtra("PREDICT_RESPONSE", Gson().toJson(predictResponse))
        }
        startActivity(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
            }
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            imageCapture = ImageCapture.Builder().setFlashMode(ImageCapture.FLASH_MODE_OFF).build()

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture
                )
            } catch (exc: Exception) {
                Log.e(TAG, "Failed to bind camera use cases", exc)
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun setUpAction() {
        binding.buttonBackInCamera.setOnClickListener {
            Intent(this, MainActivity::class.java).apply {
                val options = ActivityOptions.makeCustomAnimation(
                    this@CameraActivity,
                    R.anim.slide_in_left,
                    R.anim.anim_none
                )
                startActivity(this, options.toBundle())
                finish()
            }
        }

        binding.buttonFlashInCamera.setOnClickListener {
            toggleFlash()
        }

        binding.buttonGaleryInCamera.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, REQUEST_CODE_GALLERY)
        }
    }

    private fun toggleFlash() {
        imageCapture.flashMode = if (imageCapture.flashMode == ImageCapture.FLASH_MODE_OFF) {
            Toast.makeText(this, "Flash is available", Toast.LENGTH_SHORT).show()
            ImageCapture.FLASH_MODE_ON
        } else {
            Toast.makeText(this, "Flash is not available", Toast.LENGTH_SHORT).show()
            ImageCapture.FLASH_MODE_OFF
        }
        Log.d(
            TAG,
            "Flash mode set to: ${if (imageCapture.flashMode == ImageCapture.FLASH_MODE_ON) "ON" else "OFF"}"
        )
    }

    companion object {
        private const val TAG = "CameraActivity"
        private const val REQUEST_CODE_GALLERY = 1001
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK) {
            data?.data?.let { uri ->
                validateImage(uri)
            }
        }
    }
}
