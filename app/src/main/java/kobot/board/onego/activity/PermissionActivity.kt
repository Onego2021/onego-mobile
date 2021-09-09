package kobot.board.onego.activity

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import kobot.board.onego.R

class PermissionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)

        TedPermission.with(this).setPermissionListener(object : PermissionListener{
            override fun onPermissionGranted() {
                startActivity(Intent(this@PermissionActivity, LoginActivity::class.java))
                finish()
            }

            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                for(i in deniedPermissions!!){
                }
            }

        }).setDeniedMessage("앱을 실행하려며 권한을 허가하셔야합니다.")
            .setPermissions(Manifest.permission.CAMERA)
            .check()

    }
}
