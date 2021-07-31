package kobot.board.onego.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.graphics.Matrix
import android.graphics.drawable.Drawable
import android.hardware.Camera
import android.hardware.Sensor
import android.hardware.SensorManager
import android.hardware.camera2.*
import android.media.ExifInterface
import android.media.ImageReader
import android.net.Uri
import android.os.*
import androidx.appcompat.app.AppCompatActivity
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
import kobot.board.onego.util.CameraPreview
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.Collections.rotate

class CameraActivity : AppCompatActivity() {

    var mCamera : Camera? = null

    var mHandler : Handler? = null

    var height : Int = 0
    var width : Int = 0

    lateinit var spinBtn : ImageButton
    lateinit var closeBtn : ImageButton
    lateinit var shutterBtn : ImageButton
    lateinit var cameraPreview : SurfaceView
    lateinit var camera_frameLayout : FrameLayout
    var flashBtnSwitch = 0
    var stream = ByteArrayOutputStream()

    private fun setupCamera(){
        if(mCamera == null){
            mCamera = Camera.open()
        }
        cameraPreview = CameraPreview(this, mCamera!!)
        camera_frameLayout.addView(cameraPreview)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        spinBtn = findViewById(R.id.spinBtn)
        closeBtn = findViewById(R.id.closeBtn)
        camera_frameLayout = findViewById(R.id.cameraFrame)
        shutterBtn = findViewById(R.id.shutterBtn)

        setupCamera()

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
            mCamera?.takePicture(null, null, mPicture)

            intent = Intent(this, ImageActivity::class.java)
            var byteArray = stream.toByteArray()
            intent.putExtra("image", byteArray)
            intent.putExtra("width", width)
            intent.putExtra("height", height)
            startActivity(intent)
        }
    }
    private var mPicture = Camera.PictureCallback{data, _ ->
        val pictureFile: File? = getOutputMediaFile(); run{
            return@PictureCallback
        }
        try{
            var realImg : Bitmap
            val fos = FileOutputStream(pictureFile)
            realImg = BitmapFactory.decodeByteArray(data, 0, data.size)
            var exif = ExifInterface(pictureFile.toString())
            if(exif.getAttribute(ExifInterface.TAG_ORIENTATION).equals("6")){
                realImg = rotate(realImg, 90f)
            }else if(exif.getAttribute(ExifInterface.TAG_ORIENTATION).equals("8")){
                realImg = rotate(realImg, 270f)
            }else if(exif.getAttribute(ExifInterface.TAG_ORIENTATION).equals("3")){
                realImg = rotate(realImg, 180f)
            }else if(exif.getAttribute(ExifInterface.TAG_ORIENTATION).equals("0")){
                realImg = rotate(realImg, 90f)
            }
            realImg.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            realImg.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            fos.close()
            sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://$pictureFile")))
        }catch(e : FileNotFoundException){
        }catch (e:IOException){}
    }
    private fun rotate(bitmap:Bitmap, degree:Float): Bitmap{
        width = bitmap.width
        height = bitmap.height
        var mtx : Matrix = Matrix()
        mtx.setRotate(degree)
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, mtx, true)
    }

    private fun getOutputMediaFile(): File? {
        var mediaStorageDir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "onego")
        mediaStorageDir.apply{
            if(!exists()){
                if(!mkdirs()){
                    return null
                }
            }
        }
        val timestamp = SimpleDateFormat("yyyyMMDD_HHmmss").format(Date())
        return File("${mediaStorageDir.absolutePath}${File.separator}IMG_$timestamp.jpg")
    }

}