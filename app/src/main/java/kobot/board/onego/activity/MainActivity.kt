package kobot.board.onego.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import kobot.board.onego.R
import kobot.board.onego.adapter.MainAdapter
import kobot.board.onego.util.FileList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.io.File
import java.nio.file.Files
import java.nio.file.attribute.BasicFileAttributes
import java.nio.file.attribute.FileTime

class MainActivity : AppCompatActivity() {
    lateinit var cameraBtn : Button
    lateinit var galleryBtn : Button
    lateinit var fileRecyclerView : RecyclerView
    var fileList = arrayListOf<FileList>()
    var recentFile = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"onego_data/proofread/")

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CoroutineScope(Dispatchers.Main).async {
            CoroutineScope(Dispatchers.IO).async {
                fileList.clear()
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    recentFileAdder()
                }
            }.await()
            if (fileList.size == 0) {
                fileList.add(FileList("최근 파일이 없습니다."))
            }

            fileRecyclerView = findViewById(R.id.fileListView)

            val mAdapter = MainAdapter(applicationContext, fileList)
            fileRecyclerView.adapter = mAdapter

            val lm = LinearLayoutManager(applicationContext)
            fileRecyclerView.layoutManager = lm
            fileRecyclerView.setHasFixedSize(true)
        }

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
    @RequiresApi(Build.VERSION_CODES.O)
    fun recentFileAdder(){
        if(!recentFile.exists())recentFile.mkdirs()
        File(recentFile.toString())
            .walk()
            .forEach {
                val attributes = Files.readAttributes(it.toPath(), BasicFileAttributes::class.java)
                val name = it.name
                val KBSize : Int = (attributes.size() / 1024).toInt()
                val time : FileTime = attributes.creationTime()

                fileList.add(FileList(name, time, KBSize.toString()+"KB"))
            }
        fileList.sortBy {
            it.time
        }

    }

}