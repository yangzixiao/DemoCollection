package com.yzx.democollection.ui.chip

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEachIndexed
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.yzx.democollection.R
import com.yzx.democollection.databinding.ActivityChipBinding

class ChipActivity:AppCompatActivity() {

    lateinit var binding:ActivityChipBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityChipBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val chipDrawable = binding.chip.chipDrawable

    }
}