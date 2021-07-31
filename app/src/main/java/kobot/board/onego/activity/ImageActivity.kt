package kobot.board.onego.activity

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import kobot.board.onego.R

class ImageActivity : AppCompatActivity() {

    lateinit var backBtn : ImageButton
    lateinit var cropBtn : ImageButton
    lateinit var spinBtn : ImageButton
    lateinit var sendBtn : Button
    lateinit var manuscriptPaperWholeImg : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        backBtn = findViewById(R.id.backButton)
        cropBtn = findViewById(R.id.cropButton)
        spinBtn = findViewById(R.id.spinButton)
        sendBtn = findViewById(R.id.sendButton)
        var extras : Bundle? = intent.extras
        var width = extras?.getInt("width")
        var height = extras?.getInt("height")

        manuscriptPaperWholeImg = findViewById(R.id.manuscriptPaperWholeIMG)
        var byteArray = intent.getByteArrayExtra("image")
        var bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray!!.size)
        manuscriptPaperWholeImg.setImageBitmap(bitmap)



        backBtn.setOnClickListener {
            finish()
        }
        cropBtn.setOnClickListener {
            cropImg(manuscriptPaperWholeImg.background)
        }
        spinBtn.setOnClickListener {
            spinImg(manuscriptPaperWholeImg.background)
        }
        sendBtn.setOnClickListener {
            sendServer(manuscriptPaperWholeImg.background)
        }
    }

    private fun sendServer(background: Drawable?) {
        // send IMG to main server
        finish()
        startActivity(Intent(this, AfterActivity::class.java))
    }

    private fun spinImg(background: Drawable?) {
        // spin Image
    }

    private fun cropImg(background: Drawable?) {
        // show Crop tool on Image
    }
}