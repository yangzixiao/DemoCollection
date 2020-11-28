package com.yzx.democollection.ui.search

import android.annotation.TargetApi
import android.app.SharedElementCallback
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.transition.Transition
import android.transition.TransitionSet
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.yzx.democollection.R
import com.yzx.democollection.databinding.ActivitySearchBinding
import com.yzx.democollection.utils.TransitionUtils
import com.yzx.democollection.widget.transitions.CircularReveal
import com.yzx.lib_core.utils.AndroidVersion

class SearchActivity : AppCompatActivity() {
//
//    lateinit var searchView: SearchView
//    private lateinit var searchBack: View
//    private lateinit var scrim: View

        lateinit var binding: ActivitySearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        searchView = findViewById(R.id.searchView)
//        searchBack = findViewById(R.id.searchBack)
//        scrim = findViewById(R.id.scrim)
        setupSearchView()
        setupTransitions()
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) { // 当手机系统低于6.0时，使用代码的方式隐藏SearchView hint图标
            try {
                val magId = resources.getIdentifier("android:id/search_mag_icon", null, null)
                val magImage: ImageView = binding.searchView.findViewById(magId)
                magImage.layoutParams = LinearLayout.LayoutParams(0, 0)
            } catch (e: Exception) {

            }
        }
    }


    private fun setupSearchView() {
        binding.searchView.apply {
            queryHint = "输入内容"
            inputType = InputType.TYPE_TEXT_FLAG_CAP_WORDS
            imeOptions = imeOptions or EditorInfo.IME_ACTION_SEARCH or
                    EditorInfo.IME_FLAG_NO_EXTRACT_UI or EditorInfo.IME_FLAG_NO_FULLSCREEN
//            setOnQueryTextListener(object :androidx.appcompat.widget.SearchView.OnQueryTextListener{
//                override fun onQueryTextSubmit(query: String): Boolean {
//                    return true
//                }
//
//                override fun onQueryTextChange(query: String): Boolean {
//                    if (TextUtils.isEmpty(query)) {
//                    }
//                    return true
//                }
//
//            })
        }

        binding.searchBack.setOnClickListener {
            dismiss()
        }
        binding.scrim.setOnClickListener {
            dismiss()
        }

    }

    @TargetApi(22)
    private fun setupTransitions() {
        if (AndroidVersion.hasLollipopMR1()) {
            setEnterSharedElementCallback(object : SharedElementCallback() {
                override fun onSharedElementStart(
                    sharedElementNames: List<String>,
                    sharedElements: List<View>?,
                    sharedElementSnapshots: List<View>
                ) {
                    if (sharedElements != null && sharedElements.isNotEmpty()) {
                        val searchIcon = sharedElements[0]
                        if (searchIcon.id != R.id.searchBack) return
                        val centerX = (searchIcon.left + searchIcon.right) / 2
                        val returnTransition = window.returnTransition
                        Log.e("TAG", "onSharedElementStart: ")
                        val hideResults = TransitionUtils.findTransition(
                            returnTransition as TransitionSet,
                            CircularReveal::class.java,
                            R.id.resultsContainer
                        ) as CircularReveal?
                        hideResults?.setCenter(Point(centerX, 0))
                    }
                }
            })

            window.enterTransition.addListener(object :
                TransitionUtils.TransitionListenerAdapter() {
                override fun onTransitionEnd(transition: Transition) {
                    binding.searchView.requestFocus()
//                    showKeyboard()
                }
            })
        } else {
            binding.searchView.requestFocus()
//            GifFun.getHandler().postDelayed(100) {
//                showKeyboard()
//            }
        }
    }

    private fun dismiss() {
        // clear the background else the touch ripple moves with the translation which looks bad
        binding.searchBack.background = null
        if (AndroidVersion.hasLollipop()) {
            finishAfterTransition()
        } else {
            finish()
        }
    }
}