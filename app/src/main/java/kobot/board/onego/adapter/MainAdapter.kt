package kobot.board.onego.adapter

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kobot.board.onego.R
import kobot.board.onego.util.FileList
import java.io.File
import java.util.ArrayList

class MainAdapter(val context : Context, val fileList : ArrayList<FileList>) : RecyclerView.Adapter<MainAdapter.Holder>() {

    inner class Holder(itemView : View?) : RecyclerView.ViewHolder(itemView!!){
        val fileName = itemView?.findViewById<TextView>(R.id.filetitle)
        val fileSize = itemView?.findViewById<TextView>(R.id.filesize)
        val fileTime = itemView?.findViewById<TextView>(R.id.filetime)

        fun bind (file : FileList, context: Context){
            fileName?.text = file.filename
            fileSize?.text = file.size
            fileTime?.text = file.time.toString().substring(0, 10)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val v = LayoutInflater.from(context).inflate(R.layout.main_rv_item, parent, false)
        return Holder(v)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(fileList[position], context)
    }

    override fun getItemCount(): Int {
        return fileList.size
    }

}