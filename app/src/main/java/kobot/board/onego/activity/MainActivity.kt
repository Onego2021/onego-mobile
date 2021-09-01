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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kobot.board.onego.R
import kobot.board.onego.adapter.MainAdapter
import kobot.board.onego.util.FileList
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {
    lateinit var cameraBtn : Button
    lateinit var galleryBtn : Button
    lateinit var fileRecyclerView : RecyclerView
    var fileList = arrayListOf<FileList>(
        FileList("onego1.txt","1일 전", "14KB"),
        FileList("original.txt","2일 전", "22KB"),
        FileList("original.png","3일 전", "21KB")
    )

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fileRecyclerView = findViewById(R.id.fileListView)

        val mAdapter = MainAdapter(this, fileList)
        fileRecyclerView.adapter = mAdapter

        val lm = LinearLayoutManager(this)
        fileRecyclerView.layoutManager = lm
        fileRecyclerView.setHasFixedSize(true)

        cameraBtn = findViewById(R.id.cameraButton)
        galleryBtn = findViewById(R.id.galleryButton)

        cameraBtn.setOnClickListener {
            val intent = Intent(this, ImageActivity::class.java)
            intent.putExtra("status", "CAMERA")
            startActivity(intent)
            finish()
        }

        galleryBtn.setOnClickListener {
            val intent = Intent(this, ImageActivity::class.java)
            intent.putExtra("status", "GALLERY")
            startActivity(intent)
            finish()
        }
    }

}