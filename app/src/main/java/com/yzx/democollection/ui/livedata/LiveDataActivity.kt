package com.yzx.democollection.ui.livedata

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yzx.democollection.databinding.ActivityLivedataBinding

class LiveDataActivity : AppCompatActivity() {
    companion object {
        const val TAG = "AppCompatActivity"
    }

    private var version = -1
    private lateinit var binding: ActivityLivedataBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLivedataBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val liveDataViewModel = ViewModelProvider.NewInstanceFactory()
//            .create(LiveDataViewModel::class.java)
//        liveDataViewModel.stringLiveData.observe(this) {
//                binding.des.text = it
//                Log.e(TAG, "onCreate: $version")
//            }
//
//        binding.changeLiveDataValue.setOnClickListener {
//            version++
//            liveDataViewModel.stringLiveData.value= version.toString()
//        }

//        LiveDataInstance.liveData.observe(this){
//            binding.des.text=it
//        }

        LiveDataInstance.unPeekLiveData.observeInActivity(this, Observer {
            binding.des.text=it
        })
        binding.changeLiveDataValue.setOnClickListener {
            version++
            LiveDataInstance.unPeekLiveData.value= version.toString()
        }

    }
}