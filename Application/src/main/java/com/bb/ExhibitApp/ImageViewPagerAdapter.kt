package com.bb.ExhibitApp

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class ImageViewPagerAdapter(private val mcontext: Context, private val mImageIds: List<String>): PagerAdapter() {
//    private val mImageIds: IntArray = intArrayOf(R.drawable.appicon, R.drawable.border)
//    private val imageLinks: ArrayList<String> = arrayListOf(Exhibit().imageX)

    override fun getCount(): Int {
        return mImageIds.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView: ImageView = ImageView(mcontext)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
//        imageView.setImageResource(mImageIds[position])

            Picasso.with(mcontext)
                .load(mImageIds[position])
                .fit()
//                .resize(imageView.getMeasuredWidth(), imageView.getMeasuredWidth())
                .centerInside()
//                .centerCrop()
//                .transform(CropSquareTransformation())
                .into(imageView, object : Callback {
                    override fun onSuccess() {}
                    override fun onError() {
                        println("Image load ERROR! ")
                        Picasso.with(mcontext)
                            .load(mImageIds[position+1])
                            .fit()
                            .centerInside()
                            .into(imageView)
                    }
                })




            container.addView(imageView, 0)

        return imageView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ImageView);
    }

}

//class CropSquareTransformation : Transformation {
//    override fun transform(source: Bitmap): Bitmap {
//        val size = Math.min(source.width, source.height)
//        val x = (source.width - size) / 2
//        val y = (source.height - size) / 2
//        val result = Bitmap.createBitmap(source, x, y, size, size)
//        if (result != source) {
//            source.recycle()
//        }
//        return result
//    }
//
//    override fun key(): String {
//        return "square()"
//    }
//}
