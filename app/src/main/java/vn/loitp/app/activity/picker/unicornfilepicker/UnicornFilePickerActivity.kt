package vn.loitp.app.activity.picker.unicornfilepicker

import abhishekti7.unicorn.filepicker.UnicornFilePicker
import abhishekti7.unicorn.filepicker.utils.Constants
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import androidx.core.app.ActivityCompat
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_unicorn_file_picker.*
import vn.loitp.app.R

@LogTag("UnicornFilePickerActivity")
@IsFullScreen(false)
class UnicornFilePickerActivity : BaseFontActivity() {

    companion object {
        private const val REQUEST_CODE = 1
        private val PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_unicorn_file_picker
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    override fun onResume() {
        super.onResume()
        if (!hasPermissions(*PERMISSIONS)) {
            ActivityCompat.requestPermissions(
                this,
                PERMISSIONS,
                REQUEST_CODE
            )
        }
    }

    private fun setupViews() {
        btPhoto.setSafeOnClickListener {
            if (hasPermissions(*PERMISSIONS)) {
                UnicornFilePicker.from(this)
                    .addConfigBuilder()
                    .selectMultipleFiles(false)
                    .showOnlyDirectory(false)
                    .setRootDirectory(Environment.getExternalStorageDirectory().absolutePath)
                    .showHiddenFiles(false)
                    .setFilters(arrayOf("png", "jpg", "jpeg"))
                    .addItemDivider(true)
                    .theme(R.style.UnicornFilePicker_Dracula)
                    .build()
                    .forResult(Constants.REQ_UNICORN_FILE)
            } else {
                showPermissionsErrorAndRequest()
            }
        }
        btVideo.setSafeOnClickListener {
            if (hasPermissions(*PERMISSIONS)) {
                UnicornFilePicker.from(this)
                    .addConfigBuilder()
                    .selectMultipleFiles(true)
                    .showOnlyDirectory(false)
                    .setRootDirectory(Environment.getExternalStorageDirectory().absolutePath)
                    .showHiddenFiles(false)
                    .setFilters(arrayOf("mp4", "mp3"))
                    .addItemDivider(true)
                    .theme(R.style.UnicornFilePicker_Default)
                    .build()
                    .forResult(Constants.REQ_UNICORN_FILE)
            } else {
                showPermissionsErrorAndRequest()
            }
        }
    }

    private fun showPermissionsErrorAndRequest() {
        showSnackBarInfor("You need permissions before")
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri: Uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivity(intent)
    }

    private fun hasPermissions(vararg permissions: String): Boolean {
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Constants.REQ_UNICORN_FILE && resultCode == RESULT_OK) {
            val files = data?.getStringArrayListExtra("filePaths")
            var s = ""
            files?.forEach {
                s = s + "\n" + it
            }
            tv.text = s
        }
    }
}
