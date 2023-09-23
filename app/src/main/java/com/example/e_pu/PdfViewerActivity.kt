package com.example.e_pu

import android.content.Intent
import android.graphics.pdf.PdfRenderer
import android.os.Bundle
import android.os.ParcelFileDescriptor
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import android.widget.Button


class PdfViewerActivity : AppCompatActivity() {
    private lateinit var pdfRenderer: PdfRenderer
    private lateinit var viewPager: ViewPager
    private lateinit var adapter: PdfPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_viewer)

        viewPager = findViewById(R.id.viewPager)
        val backButton = findViewById<Button>(R.id.backButton)

        val pdfFileName = intent.getStringExtra("pdfFileName")

        if (pdfFileName != null) {
            try {
                displayPdf(pdfFileName)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        backButton.setOnClickListener {
            // Navigate back to the home activity
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }
    }

    @Throws(IOException::class)
    private fun displayPdf(pdfFileName: String) {
        // Open the PDF using PdfRenderer
        val assetManager = assets
        val inputStream = assetManager.open(pdfFileName)
        val file = File(cacheDir, "temp.pdf")
        val outputStream = FileOutputStream(file)

        val buffer = ByteArray(1024)
        var bytesRead: Int

        while (inputStream.read(buffer).also { bytesRead = it } != -1) {
            outputStream.write(buffer, 0, bytesRead)
        }

        outputStream.close()
        inputStream.close()

        val parcelFileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
        pdfRenderer = PdfRenderer(parcelFileDescriptor)

        // Create an adapter for the ViewPager
        adapter = PdfPagerAdapter(this, pdfRenderer)

        // Set the adapter to the ViewPager
        viewPager.adapter = adapter
    }
}
