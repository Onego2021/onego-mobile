package kobot.board.onego.activity

import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.drawable.Drawable
import android.media.ExifInterface
import android.net.Network
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toBitmap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kobot.board.onego.R
import kobot.board.onego.util.NetworkClient
import kobot.board.onego.util.RetrofitAPI
import kotlinx.coroutines.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

class ImageActivity : AppCompatActivity() {

    val REQUEST_IMAGE_CAPTURE = 1
    val OPEN_GALLERY = 2

    lateinit var exif : ExifInterface
    lateinit var backBtn : ImageButton
    lateinit var cropBtn : ImageButton
    lateinit var spinBtn : ImageButton
    lateinit var sendBtn : Button
    lateinit var manuscriptPaperWholeImg : ImageView

    var height : Int = 0
    var width : Int = 0
    var imgURI : Uri? = null

    private lateinit var currentPhotoPath : String

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        CoroutineScope(Dispatchers.Main).async {
            CoroutineScope(Dispatchers.IO).async {
                if (intent.getStringExtra("status") == "CAMERA"){
                    takePicture()
                }else{
                    openGallery()
                }
            }.await()
            backBtn = findViewById(R.id.backButton)
            cropBtn = findViewById(R.id.cropButton)
            spinBtn = findViewById(R.id.spinButton)
            sendBtn = findViewById(R.id.sendButton)


            manuscriptPaperWholeImg = findViewById(R.id.manuscriptPaperWholeIMG)

            backBtn.setOnClickListener {
                val mainIntent = Intent(applicationContext, MainActivity::class.java)
                startActivity(mainIntent)
                finish()
            }

            cropBtn.setOnClickListener {

                imgURI?.let { it1 -> cropImg(it1) }
            }

            spinBtn.setOnClickListener {
                spinImg(manuscriptPaperWholeImg.background)
            }

            sendBtn.setOnClickListener {
                sendServer()
            }
        }
    }

    private fun openGallery() {
        val intent : Intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("image/*")
        startActivityForResult(intent, OPEN_GALLERY)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun sendServer() {
        // send IMG to main server
        CoroutineScope(Dispatchers.Main).async{
            CoroutineScope(Dispatchers.IO).async {
                var file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"onego_data/original/before_onego.png")
                file.createNewFile()
                var drawable = manuscriptPaperWholeImg.drawable
                var bitmap = drawable.toBitmap()
                var auth = FirebaseAuth.getInstance().currentUser
                var stream = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                stream.close()
                Log.d("로그로그", auth.toString())
                //post2MainServer(file, auth)
                Log.d("로그로그", "ghghghghghg  ////////////")


            }.await()

            finish()
            startActivity(Intent(this@ImageActivity, AfterActivity::class.java))
        }



    }

   /*private fun post2MainServer(file: File, auth: FirebaseUser?) {
        var requestBody : RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(), file)
        var body : MultipartBody.Part = MultipartBody.Part.createFormData("uploaded_file", file.name, requestBody)

        var uid = RequestBody.create("text/plain".toMediaTypeOrNull(), auth.toString())
        Log.d("레트로핏 결과2",""+body.toString())

        var retrofit = NetworkClient().getRetrofit("MAIN_SERVER_URL")
        var server = retrofit.create(RetrofitAPI::class.java)

          server.post_Manuscript_Request(uid, body).enqueue(object : Callback<RequestBody>{

            override fun onResponse(call: Call<RequestBody>, response: Response<RequestBody>) {
                if (response?.isSuccessful) {
                    Toast.makeText(getApplicationContext(), "파일 인식 중...", Toast.LENGTH_LONG).show();
                    Log.d("레트로핏 결과2",""+response?.body().toString())
                } else {
                    Toast.makeText(getApplicationContext(), "파일 업로드 에러...", Toast.LENGTH_LONG).show();
                    Log.d("레트로핏 결과2","허허,,,"+response?.body().toString())
                }
            }
            override fun onFailure(call: Call<RequestBody>, t: Throwable) {
                Log.d("레트로핏 결과2","실패,,,")
            }

        })
    }
    */
    */

    private fun spinImg(background: Drawable?) {
        // spin Image
        val currentDegree = manuscriptPaperWholeImg.rotation
        ObjectAnimator.ofFloat(manuscriptPaperWholeImg, View.ROTATION, currentDegree, currentDegree + 90f) .setDuration(300) .start()
    }

    private fun cropImg(uri: Uri) {
        // show Crop tool on Image
        CropImage.activity(uri).setGuidelines(CropImageView.Guidelines.ON)
            .setCropShape(CropImageView.CropShape.RECTANGLE)
            .start(this)
    }



    private fun takePicture(){
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    null
                }
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "kobot.board.onego",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            galleryAddPic()
            setPic()
        }
        else if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            val result = CropImage.getActivityResult(data)
            if(resultCode == Activity.RESULT_OK){
                result.uri?.let {
                    manuscriptPaperWholeImg.setImageBitmap(result.bitmap)
                    manuscriptPaperWholeImg.setImageURI(result.uri)
                    imgURI = result.uri
                }
            }
        }
        else if(requestCode == OPEN_GALLERY && resultCode == RESULT_OK){
            imgURI = data!!.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imgURI)
                manuscriptPaperWholeImg.setImageBitmap(bitmap)
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun createImageFile(): File {

        val timeStamp : String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir : File? = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"onego_data/original/")
        if (!storageDir?.exists()!!){
            storageDir.mkdirs()
        }
        return File.createTempFile(
            "${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
            Log.d("test", "currentPhotoPath : $currentPhotoPath")
        }
    }

    private fun setPic() {
        width = manuscriptPaperWholeImg.width
        height = manuscriptPaperWholeImg.height
        var targetW: Int
        var targetH: Int
        val bmOptions = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
            targetW = outWidth
            targetH = outHeight


            val scaleFactor: Int = Math.min(targetW/width, targetH/height)
            inJustDecodeBounds = false
            inSampleSize = scaleFactor
        }
        var bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions)
        var stream = ByteArrayOutputStream()

        try{
            exif = ExifInterface(currentPhotoPath)
            var exifOrientation = 0
            var exifDegree = 0

            if (exif != null) {
                exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL)
                exifDegree = exifOrientationToDegress(exifOrientation)
            }

            manuscriptPaperWholeImg.setImageBitmap(rotate(bitmap, exifDegree))
        }catch (e : IOException){
            e.printStackTrace()
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val path = MediaStore.Images.Media.insertImage(applicationContext.contentResolver, bitmap, "Title", null)
        imgURI = Uri.parse(path)
        var byteArray : ByteArray = stream.toByteArray()
    }

    private fun rotate(bitmap: Bitmap, degree: Int) : Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degree.toFloat())
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix,true)
    }

    private fun exifOrientationToDegress(exifOrientation: Int): Int {
        when(exifOrientation){
            ExifInterface.ORIENTATION_ROTATE_90 -> return 90

            ExifInterface.ORIENTATION_ROTATE_180 -> return 180

            ExifInterface.ORIENTATION_ROTATE_270 -> return 270

            else -> return 0
        }
    }

    private fun galleryAddPic() {
        Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also { mediaScanIntent ->
            Log.d("test", "currentPhotoPath2 : $currentPhotoPath")
            val f = File(currentPhotoPath)
            mediaScanIntent.data = Uri.fromFile(f)
            sendBroadcast(mediaScanIntent)
        }
    }


}