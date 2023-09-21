import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.e_pu.R  // Import your app's R class
import java.io.File

class PdfViewerActivity : AppCompatActivity() {
    private lateinit var pdfRenderer: PdfRenderer
    private lateinit var pdfPage: PdfRenderer.Page
    private lateinit var pdfImageView: ImageView
    private lateinit var backButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_viewer)

        pdfImageView = findViewById(R.id.pdfviewer)
        backButton = findViewById(R.id.exitButton)

       val pdfFileName = intent.getStringExtra("Unit.1.pdf")


        if (pdfFileName != null) {
            displayPdf(pdfFileName)
        }

        backButton.setOnClickListener {
            finish() // Close the PDF viewer activity
        }
    }

    private fun displayPdf(pdfFileName: String) {
        val pdfFile = File(filesDir, pdfFileName)
        val parcelFileDescriptor: ParcelFileDescriptor = ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY)
        pdfRenderer = PdfRenderer(parcelFileDescriptor)
        pdfPage = pdfRenderer.openPage(0)

        val bitmap: Bitmap = Bitmap.createBitmap(pdfPage.width, pdfPage.height, Bitmap.Config.ARGB_8888)
        pdfPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
        pdfImageView.setImageBitmap(bitmap)
    }

    override fun onDestroy() {
        super.onDestroy()
        pdfPage.close()
        pdfRenderer.close()
    }
}
