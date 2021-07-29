package kobot.board.onego.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener
import kobot.board.onego.R

class AfterActivity : AppCompatActivity() {

    var manuscriptPaperImages = arrayOf(
        "https://raw.githubusercontent.com/sayyam/carouselview/master/sample/src/main/res/drawable/image_3.jpg",
        "https://raw.githubusercontent.com/sayyam/carouselview/master/sample/src/main/res/drawable/image_1.jpg",
        "https://raw.githubusercontent.com/sayyam/carouselview/master/sample/src/main/res/drawable/image_2.jpg"
    )
    lateinit var manuscriptPaperText : Array<String>

    var imageListener : ImageListener = object : ImageListener{
        override fun setImageForPosition(position: Int, imageView: ImageView?) {
            Picasso.get().load(manuscriptPaperImages[position]).into(imageView)
        }
    }


    lateinit var markAdder : Button
    lateinit var pdfDownLoader : Button
    lateinit var txtDownloader : Button
    lateinit var homeLoader : Button
    lateinit var carouselView : CarouselView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_after)

        manuscriptPaperText = requestText()
        // manuscriptPaperImages = requestManuscriptPaper()

        carouselView = findViewById<CarouselView>(R.id.onegoCarousel)
        carouselView.pageCount = manuscriptPaperImages.size
        carouselView.setImageListener(imageListener)

        markAdder = findViewById(R.id.markAdder)
        pdfDownLoader = findViewById(R.id.pdfDownloader)
        txtDownloader = findViewById(R.id.txtDownloader)
        homeLoader = findViewById(R.id.homeLoader)

        markAdder.setOnClickListener {
            addMark(manuscriptPaperImages)
        }

        pdfDownLoader.setOnClickListener {
            downloadImgtoPDF(manuscriptPaperImages)
        }

        txtDownloader.setOnClickListener {
            downloadImgtoText(manuscriptPaperImages)
        }

        homeLoader.setOnClickListener {
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }

    }

    // Download Manuscript paper Img to PDF
    private fun downloadImgtoPDF(manuscriptPaperImages: Array<String>) {
    }

    // Download Manuscript paper Img to txt
    private fun downloadImgtoText(manuscriptPaperImages: Array<String>) {
    }

    // Request ManuscriptPaper txt to server
    private fun requestText(): Array<String> {
        var manuscripttxt = arrayOf("222", "222")
        return  manuscripttxt
    }

    // Request ManuscriptPaper to server
    public fun requestManuscriptPaper() : Array<String>{
        var manuscriptPaperImgUrl = arrayOf("111", "222")
        return manuscriptPaperImgUrl
    }

    // Add mark after spell check
    private fun addMark(manuscriptPaperImages: Array<String>) {
    }

}
