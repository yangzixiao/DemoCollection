package com.yzx.lib_scan

import android.Manifest
import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.os.Vibrator
import android.provider.MediaStore
import android.text.TextUtils
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import android.widget.FrameLayout.LayoutParams.MATCH_PARENT
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.huawei.hms.hmsscankit.RemoteView
import com.huawei.hms.hmsscankit.ScanUtil
import com.huawei.hms.ml.scan.HmsScan
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions
import com.permissionx.guolindev.PermissionX
import java.io.IOException

class ScanActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_CODE_SCAN = 1
        const val SCAN_RESULT = "scanResult"
    }


    private lateinit var remoteView: RemoteView
    private lateinit var scanCursor: ImageView
    private lateinit var scanCursorAnim: ObjectAnimator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        val frameLayout = findViewById<FrameLayout>(R.id.rim)
        scanCursor = findViewById(R.id.scan_cursor)
        findViewById<ImageView>(R.id.ivFlash).setOnClickListener {
            remoteView.switchLight()
        }
        //打开相册
        findViewById<ImageView>(R.id.toGallery).setOnClickListener {
            requestReadStoragePermission()
        }

        // Obtain the screen size.
        val mScreenWidth = resources.displayMetrics.widthPixels
        val mScreenHeight = resources.displayMetrics.heightPixels
        val rect = Rect(0, 0, mScreenWidth, mScreenHeight)

        //Initialize the RemoteView instance, and set callback for the scanning result.
        remoteView = RemoteView.Builder()
            .setContext(this)
            .setBoundingBox(rect)
            .setIsCustomView(true)
            .setFormat(HmsScan.ALL_SCAN_TYPE)
            .build()

        initScanCursorAnim()
        remoteView.pauseContinuouslyScan()
        remoteView.setOnResultCallback { result ->
            checkScanResultAndSetResult(result)
        }
        remoteView.onCreate(savedInstanceState)
        // Load the customized view to the activity.
        val params = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        frameLayout.run {
            addView(remoteView, params)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionX.init(this)
                .permissions(Manifest.permission.CAMERA)
                .onForwardToSettings { scope, deniedList ->
                    if (deniedList.isNotEmpty()) {
                        scope.showForwardToSettingsDialog(deniedList, "去开启扫码服务所需权限", "开启", "取消")
                    }
                }
                .onForwardToSettingsRefused { deniedList ->
                    if (deniedList!!.isNotEmpty()) {
                        finish()
                    }
                }
                .request { allGranted, _, _ ->
                    if (allGranted) {
                        startScanAndStartAnim()
                    } else {
                        Toast.makeText(this, "打开摄像头权限拒绝", Toast.LENGTH_SHORT).show()
                    }
                }

        } else {
            startScanAndStartAnim()
        }
    }

    private fun initScanCursorAnim() {
        val marginDp = (resources.displayMetrics.density) * 150
        scanCursorAnim = ObjectAnimator.ofFloat(
            scanCursor,
            "translationY",
            marginDp,
            resources.displayMetrics.heightPixels.toFloat() - marginDp
        )
        scanCursorAnim.apply {
            duration = 5000
            interpolator = LinearInterpolator()
            repeatCount = Int.MAX_VALUE
        }
    }

    private fun startScanAndStartAnim() {
        remoteView.resumeContinuouslyScan()
        scanCursor.visibility = View.VISIBLE
        scanCursorAnim.start()
    }

    /**
     * 打开相册权限
     */
    private fun requestReadStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionX.init(this)
                .permissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .onForwardToSettings { scope, deniedList ->
                    if (deniedList.isNotEmpty()) {
                        scope.showForwardToSettingsDialog(deniedList, "去开启扫码服务所需权限", "开启", "取消")
                    }
                }
                .request { allGranted, _, _ ->
                    if (allGranted) {
                        pickPhoto()
                    } else {
                        Toast.makeText(this, "打开相册权限拒绝", Toast.LENGTH_SHORT).show()
                    }
                }

        } else {
            pickPhoto()
        }
    }

    private fun pickPhoto() {
        val pickIntent =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        startActivityForResult(pickIntent, REQUEST_CODE_SCAN)
    }

    /**
     * 检测扫描结果、设置结果
     */
    private fun checkScanResultAndSetResult(result: Array<HmsScan>?) {
        if (result != null && result.size > 0 && result[0] != null
            && !TextUtils.isEmpty(result[0].getOriginalValue())
        ) {
            val vibrator = this.getSystemService(VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(300)
            val intent = Intent()
            intent.putExtra(SCAN_RESULT, result[0].getOriginalValue())
            setResult(RESULT_OK, intent)
            finish()
        } else {
            Toast.makeText(this, R.string.scanError, Toast.LENGTH_SHORT).show()
        }
    }


    /**
     * Call the lifecycle management method of the remoteView activity.
     */
    override fun onStart() {
        super.onStart()
        remoteView.onStart()
    }

    override fun onResume() {
        super.onResume()
        remoteView.onResume()
        ScanUtil.isScanAvailable(this)

//        scanCursorAnim.start()
    }

    override fun onPause() {
        super.onPause()
        remoteView.onPause()
//        scanCursorAnim.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        remoteView.onDestroy()
    }

    override fun onStop() {
        super.onStop()
        remoteView.onStop()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK) {
            return
        }
        if (requestCode == REQUEST_CODE_SCAN) {
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, data!!.data)
                val scanResult = ScanUtil.decodeWithBitmap(
                    this, bitmap,
                    HmsScanAnalyzerOptions.Creator().setPhotoMode(true).create()
                )
                checkScanResultAndSetResult(scanResult)
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(this, "扫码异常", Toast.LENGTH_SHORT).show()
            }
        }
    }
}