package com.serapbercin.viewpager2example.views

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.serapbercin.viewpager2example.R
import com.serapbercin.viewpager2example.adapter.CategoryAdapter
import com.serapbercin.viewpager2example.util.DummyCategoryData.categories
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.abs

class PageTransformerCategoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = CategoryAdapter()
        viewPager2.adapter = adapter
        adapter.setItem(categories)
        with(viewPager2) {
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 3
        }

        // viewPager2.setPageTransformer(ViewPager2PageTransformation())
        val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
        // val nextItemVisiblePx = 110
        val currentItemHorizontalMarginPx =
                resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
        // val currentItemHorizontalMarginPx = 40
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
            page.translationY = -pageTranslationX * abs(position)
            // Next line scales the item's height. You can remove it if you don't want this effect
            // page.scaleY = 1 - (0.45f * abs(position))
            page.scaleY = 1f
            // If you want a fading effect uncomment the next line:
            page.alpha = 0.85f + (1 - abs(position))
        }

        viewPager2.setPageTransformer(pageTransformer)
    }
}