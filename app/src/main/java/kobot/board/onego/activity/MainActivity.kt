package kobot.board.onego.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kobot.board.onego.R
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {
    lateinit var cameraBtn : Button
    lateinit var galleryBtn : Button
    lateinit var fileRecyclerView : RecyclerView

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        cameraBtn = findViewById(R.id.cameraButton)
        galleryBtn = findViewById(R.id.galleryButton)
        fileRecyclerView = findViewById(R.id.fileListView)

        cameraBtn.setOnClickListener {
            startActivity(Intent(this, CameraActivity::class.java))
            finish()
        }

        galleryBtn.setOnClickListener {
            Toast.makeText(this, "갤러리 버튼이 클릭되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }

}