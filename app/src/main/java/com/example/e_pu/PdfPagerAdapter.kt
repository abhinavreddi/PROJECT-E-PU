package com.example.e_pu

import android.content.Context
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import java.io.IOException

class PdfPagerAdapter(private val context: Context, private val pdfRenderer: PdfRenderer) : PagerAdapter() {
    private var bitmapCache = HashMap<Int, Bitmap>()

    override fun getCount(): Int {
        return pdfRenderer.pageCount
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view === obj
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView = ImageView(context)
        imageView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        val bitmap = getBitmapFromCache(position)
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap)
        } else {
            try {
                val pdfPage = pdfRenderer.openPage(position)
                bitmapCache[position] = renderPageToBitmap(pdfPage)
                pdfPage.close()
                imageView.setImageBitmap(bitmapCache[position])
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        container.addView(imageView)
        return imageView
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View)
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }

    private fun renderPageToBitmap(page: PdfRenderer.Page): Bitmap {
        val bitmap = Bitmap.createBitmap(
            page.width,
            page.height,
            Bitmap.Config.ARGB_8888
        )
        page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
        return bitmap
    }

    private fun getBitmapFromCache(position: Int): Bitmap? {
        return bitmapCache[position]
    }
}
