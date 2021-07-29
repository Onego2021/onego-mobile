package kobot.board.onego.activity

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.SurfaceView
import android.widget.ImageButton
import kobot.board.onego.R

class CameraActivity : AppCompatActivity() {

    lateinit var flashBtn : ImageButton
    lateinit var closeBtn : ImageButton
    lateinit var shutterBtn : ImageButton
    lateinit var cameraPreview : SurfaceView
    var flashBtnSwitch = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        flashBtn = findViewById(R.id.flashBtn)
        closeBtn = findViewById(R.id.closeBtn)
        cameraPreview = findViewById(R.id.cameraPreview)
        shutterBtn = findViewById(R.id.shutterBtn)

        flashBtn.setOnClickListener {
            if(flashBtnSwitch == 0){
                shutterBtn.setBackgroundResource(R.drawable.flash)
                flashBtnSwitch = 1
            }else{
                shutterBtn.setBackgroundResource(R.drawable.non_flash)
                flashBtnSwitch = 0
            }
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