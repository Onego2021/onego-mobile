package kobot.board.onego.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.ImageFormat
import android.graphics.drawable.Drawable
import android.hardware.Sensor
import android.hardware.SensorManager
import android.hardware.camera2.*
import android.media.ExifInterface
import android.media.ImageReader
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.DisplayMetrics
import android.util.Log
import android.util.SparseIntArray
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.WindowManager
import android.widget.CheckBox
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kobot.board.onego.R

class CameraActivity : AppCompatActivity() {

    lateinit var mSurfaceViewHolder : SurfaceHolder
    lateinit var mImageReader: ImageReader
    lateinit var mCameraDevice : CameraDevice
    lateinit var mPreviewBuilder : CaptureRequest.Builder
    lateinit var mSession : CameraCaptureSession

    var mHandler : Handler? = null

    var mHeight : Int = 0
    var mWidth : Int = 0

    var mCameraId = CAMERA_BACK

    companion object{
        const val CAMERA_BACK = "0"
        const val CAMERA_FRONT = "1"

        private val ORIENTATIONS = SparseIntArray()

        init {
            ORIENTATIONS.append(ExifInterface.ORIENTATION_NORMAL, 0)
            ORIENTATIONS.append(ExifInterface.ORIENTATION_ROTATE_90, 90)
            ORIENTATIONS.append(ExifInterface.ORIENTATION_ROTATE_180, 180)
            ORIENTATIONS.append(ExifInterface.ORIENTATION_ROTATE_270, 270)
        }
    }

    lateinit var spinBtn : ImageButton
    lateinit var closeBtn : ImageButton
    lateinit var shutterBtn : ImageButton
    lateinit var cameraPreview : SurfaceView
    var flashBtnSwitch = 0

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        spinBtn = findViewById(R.id.spinBtn)
        closeBtn = findViewById(R.id.closeBtn)
        cameraPreview = findViewById(R.id.cameraPreview)
        shutterBtn = findViewById(R.id.shutterBtn)

        // hide status bar
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // turn on screen
        window.setFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
        )

        spinBtn.setOnClickListener {

        }
        closeBtn.setOnClickListener {
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }
        shutterBtn.setOnClickListener {
            startActivity(Intent(this, ImageActivity::class.java))
        }
    }

}