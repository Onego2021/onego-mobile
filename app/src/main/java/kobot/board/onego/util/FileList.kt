package kobot.board.onego.util

import android.os.Environment
import java.io.File

class FileList(var filename: String, var time: String, var size: String) {
    var filePath = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"onego_data/proofread")
    init {
        if(!filePath.exists()){

        }
    }
}