package kobot.board.onego.util

import android.content.Context
import android.hardware.Camera
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.Toast
import java.io.IOError
import java.io.IOException

class CameraPreview(context: Context, private val mCamera: Camera) : SurfaceView(context), SurfaceHolder.Callback {
    private val mHolder : SurfaceHolder = this.holder.apply {
        addCallback(this@CameraPreview)
        setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        mCamera.apply {
            try {
                setDisplayOrientation(90)
                setPreviewDisplay(holder)
                startPreview()
            }catch (e: IOException){
                Log.d("Error", "Error setting camera preview : ${e.message}")
            }
        }
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        if(mHolder.surface == null){
            return
        }
        try{
            mCamera.stopPreview()
        }catch (e: Exception){
        }
        mCamera.apply {
            try{
                setPreviewDisplay(mHolder)
                startPreview()
            }catch (e: Exception){
                Log.d("Error", "Error starting camera preview : ${e.message}")
            }
        }
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        mCamera.stopPreview()
    }

}