package com.serapbercin.viewpager2example.transformer

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.serapbercin.viewpager2example.R
import kotlin.math.abs

class ViewPager2PageTransformation : ViewPager2.PageTransformer {

    companion object {
        private const val MIN_SCALE = 0.85f
        private const val MIN_ALPHA = 0.5f
    }

    /*override fun transformPage(page: View, position: Float) {
        // Log.d("XXX", "transformPage")
        val absPos = abs(position)
        page.apply {
            // translationY = absPos * 500f
            translationX = absPos * 500f
            scaleX = 1f
            scaleY = 1f
        }

        Log.d("XXX", "position $position")
        when {
            position < -1 -> {
                page.alpha = 0.5f
            }
            position <= 1 -> {
                // left
                // page.alpha = max(0.2f, 1 - abs(position))
                page.alpha = 0.9f
                // page.translationY = absPos * 100f
            }
            else -> {
                page.translationY = absPos * 100f
                page.alpha = 0.5f
            }
        }
    }*/

    override fun transformPage(view: View, position: Float) {
        view.apply {
            val pageWidth = width
            val pageHeight = height
            val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
            // val nextItemVisiblePx = 110
            val currentItemHorizontalMarginPx =
                    resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
            // val currentItemHorizontalMarginPx = 40
            val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
            translationX = -pageTranslationX * position
            when {
                position < -1 -> { // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    // alpha = 0.5f
                    // translationY = 0f
                    // translationX = -200f
                    scaleX = 1f
                    scaleY = 1f
                }
                position <= 1 -> { // [-1,1]
                    // Modify the default slide transition to shrink the page as well
                    val scaleFactor = Math.max(MIN_SCALE, 1 - abs(position))
                    val vertMargin = pageHeight * (1 - scaleFactor) / 2
                    val horzMargin = pageWidth * (1 - scaleFactor) / 2
                    /*translationY = if (position < 0) {
                        -(horzMargin + vertMargin / 2)
                    } else {
                        -(horzMargin - vertMargin / 2)
                    }*/

                    // Scale the page down (between MIN_SCALE and 1)
                    scaleX = scaleFactor
                    scaleY = scaleFactor

                    // Fade the page relative to its size.
                    /*alpha = (MIN_ALPHA +
                            (((scaleFactor - MIN_SCALE) / (1 - MIN_SCALE)) * (1 - MIN_ALPHA)))*/
                }
                else -> { // (1,+Infinity]
                    // This page is way off-screen to the right.
                    // alpha = 0.5f
                    translationY = 0f
                    translationX = -pageTranslationX * position
                    scaleX = 1f
                    scaleY = 1f
                }
            }
        }
    }
}