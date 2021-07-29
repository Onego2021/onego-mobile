package kobot.board.onego.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import kobot.board.onego.R

class MainActivity : AppCompatActivity() {
    lateinit var cameraBtn : Button
    lateinit var galleryBtn : Button

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var display = windowManager.defaultDisplay
        var outMetrics = DisplayMetrics()
        display.getRealMetrics(outMetrics)

        var density = resources.displayMetrics.density

        cameraBtn = findViewById(R.id.cameraButton)
        galleryBtn = findViewById(R.id.galleryButton)

        cameraBtn.height = (outMetrics.heightPixels / density).toInt()
        galleryBtn.height = cameraBtn.height

        cameraBtn.setOnClickListener {
            startActivity(Intent(this, CameraActivity::class.java))
            finish()
        }

        galleryBtn.setOnClickListener {
            Toast.makeText(this, "갤러리 버튼이 클릭되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }
}