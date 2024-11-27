package vn.edu.hust.activityexamples

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SecondActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_second)

    findViewById<Button>(R.id.button_close).setOnClickListener {
      finish()
    }

    val textContent = findViewById<TextView>(R.id.text_content)
    val value1 = intent.getIntExtra("param3", 0)
    val value2 = intent.getDoubleExtra("param2", 0.0)
    val value3 = intent.getStringExtra("param1")
    textContent.text = "$value1\n$value2\n$value3"
  }
}