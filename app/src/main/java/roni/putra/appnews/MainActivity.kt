package roni.putra.appnews

import android.app.Activity
import android.app.ComponentCaller
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.github.dhaval2404.imagepicker.ImagePicker
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import roni.putra.appnews.config.Network
import roni.putra.appnews.config.Util
import roni.putra.appnews.model.UploadModel
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var btnSelectFile: Button
    private lateinit var btnUploadFile: Button

    private lateinit var tvSelectedFile: TextView

    private var selectedFileUri: Uri? = null

    private lateinit var util: Util

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        util = Util(this)

        btnSelectFile = findViewById(R.id.btnSelectFile)
        btnUploadFile = findViewById(R.id.btnUploadFile)
        tvSelectedFile = findViewById(R.id.tvSelectedFile)


        //checkSelfPermission Storage
        util.checkStoragePermission()

        btnSelectFile.setOnClickListener {
            selectFile()
        }

        btnUploadFile.setOnClickListener {
            /*
            selectedFileUri?.let { uri ->
                uploadImage(uri)
            }

             */
            if (selectedFileUri != null ) {
                uploadImage(selectedFileUri!!)
            }
        }

    }

    private val selectFileLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                selectedFileUri = it

                val fileName = getFileNameFromUridua(it)
                tvSelectedFile.text = "Selected file: $fileName"
                btnUploadFile.visibility = Button.VISIBLE
            }
        }

    private fun selectFile() {
        selectFileLauncher.launch("application/pdf")
    }

    private fun getFileNameFromUridua(uri: Uri): String? {
        var fileName: String? = null
        val cursor = contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (nameIndex != -1) {
                    fileName = it.getString(nameIndex)
                }
            }
        }
        return fileName
    }


    private fun uploadImage(uri: Uri) {

        val file = util.getFileNameFromUri(uri) ?: run {
            Toast.makeText(this, "Failed to create file from Uri", Toast.LENGTH_SHORT).show()
            return
        }

        val requestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val part = MultipartBody.Part.createFormData("file", file.name, requestBody)


        val response = Network.service().uploadFile(part)
        response.enqueue(object : Callback<UploadModel> {
            override fun onResponse(call: Call<UploadModel>, response: Response<UploadModel>) {

                val hasil = response.body()
                Toast.makeText(this@MainActivity, hasil!!.message, Toast.LENGTH_LONG).show()


            }

            override fun onFailure(call: Call<UploadModel>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Ada kesalahan ${t.message}", Toast.LENGTH_LONG)
                    .show()
                Log.e("Kesalahan", t.message.toString())
            }
        })


    }


}