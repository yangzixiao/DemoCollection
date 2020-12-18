package com.yzx.democollection.ui.badgedrawable

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yzx.democollection.databinding.ActivityBadgeDrawableBinding


class BadgeDrawableActivity : AppCompatActivity() {

    lateinit var binding: ActivityBadgeDrawableBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBadgeDrawableBinding.inflate(layoutInflater)
        setContentView(binding.root)


        BadgeFactory.createCircle(this)
//            .setTextColor(Color.WHITE)
//            .setWidthAndHeight(18, 18)
//            .setBadgeBackground(Color.RED)
//            .setTextSize(8)
//            .setBadgeGravity(Gravity.END or Gravity.TOP)
            .setBadgeCount(20)
//            .setShape(BadgeView.SHAPE_CIRCLE)
//            .setSpace(10, 10)
            .bind(binding.badgeImage)

    }
}