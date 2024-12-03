package roni.putra.appnews

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import jp.wasabeef.richeditor.RichEditor

class BeritaActivity : AppCompatActivity() {
    private lateinit var richEditor: RichEditor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_berita)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        richEditor = findViewById(R.id.richEditor)
        richEditor.setEditorHeight(200)
        richEditor.setEditorFontSize(18)
        richEditor.setEditorFontColor(android.graphics.Color.BLACK)
        richEditor.setPadding(10, 10, 10, 10)
        richEditor.setPlaceholder("Type here...")

        // Bold button
        findViewById<Button>(R.id.boldButton).setOnClickListener {
            richEditor.setBold()
        }

        // Italic button
        findViewById<Button>(R.id.italicButton).setOnClickListener {
            richEditor.setItalic()
        }

        // Underline button
        findViewById<Button>(R.id.underlineButton).setOnClickListener {
            richEditor.setUnderline()
        }

    }
}