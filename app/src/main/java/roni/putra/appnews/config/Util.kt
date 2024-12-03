package roni.putra.appnews.config

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.OpenableColumns
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream


open class Util(private val context: Context) {

    private val storagePermissionCode = 101

    fun checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                storagePermissionCode
            )
        } else {
            ActivityCompat.requestPermissions(
                context as Activity, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1
            )
        }
    }

    fun getFileNameFromUri(uri: Uri): File? {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val file = File(context.cacheDir, "temp_file_${System.currentTimeMillis()}.pdf")

        inputStream?.use { input ->
            FileOutputStream(file).use { output ->
                input.copyTo(output)
            }
        }
        return if (file.exists()) file else null
    }


}