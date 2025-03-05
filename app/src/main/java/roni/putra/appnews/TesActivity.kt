package roni.putra.appnews

import android.app.Activity
import android.app.ComponentCaller
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.github.dhaval2404.imagepicker.ImagePicker
import java.io.File

class TesActivity : AppCompatActivity() {
    private lateinit var btnSelectFile: Button
    private lateinit var tvTes: TextView

    private var imageFile: File? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tes)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnSelectFile = findViewById(R.id.btnSelectFile)
        tvTes = findViewById(R.id.tvTes)

        tvTes.text = unhex("58g888786858483",82)

        btnSelectFile.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?,) {
        super.onActivityResult(requestCode, resultCode, data,)
        if (resultCode == Activity.RESULT_OK && data != null) {
            val uri = data.data!!
            imageFile = File(uri.path!!)
           // btnUploadFile.visibility = Button.VISIBLE
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }

    }

    private fun unhex(str: String = "", code: Int = 0): String {
        val parts = str.split("g") // Memisahkan string berdasarkan karakter 'g'
        if (parts.size < 2) return "" // Validasi input

        val head = parts[0].toInt(16) - code // Konversi bagian pertama ke bilangan desimal
        val content = parts[1] // Bagian kedua adalah konten

        if (head == content.length / 2) {
            var result = StringBuilder()

            for (i in 0 until head) {
                val hexPair = content.substring(i * 2, (i * 2) + 2) // Ambil 2 karakter heksadesimal
                val charValue = hexPair.toInt(16) - code // Konversi ke karakter dengan pengurangan kode
                result.append(charValue.toChar())
            }

            return result.reverse().toString() // Balikkan hasilnya
        }

        return ""
    }

}