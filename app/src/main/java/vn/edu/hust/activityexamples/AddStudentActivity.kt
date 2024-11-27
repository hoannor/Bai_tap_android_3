package vn.edu.hust.activityexamples

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddStudentActivity : AppCompatActivity() {

  private lateinit var edtHoten: EditText
  private lateinit var edtMssv: EditText
  private var isEdit = false
  private var position = -1

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_add_student)

    edtHoten = findViewById(R.id.edit_hoten)
    edtMssv = findViewById(R.id.edit_mssv)

    // Kiểm tra nếu đây là chế độ chỉnh sửa
    val hoten = intent.getStringExtra("hoten") ?: ""
    val mssv = intent.getStringExtra("mssv") ?: ""
    position = intent.getIntExtra("position", -1)

    if (position != -1) {
      isEdit = true
      edtHoten.setText(hoten)  // Hiển thị tên sinh viên
      edtMssv.setText(mssv)    // Hiển thị mã sinh viên
    }

    findViewById<Button>(R.id.button_ok).setOnClickListener {
      val newHoten = edtHoten.text.toString()
      val newMssv = edtMssv.text.toString()

      val resultIntent = Intent()
      resultIntent.putExtra("hoten", newHoten)
      resultIntent.putExtra("mssv", newMssv)

      if (isEdit && position != -1) {
        // Nếu đang chỉnh sửa, gửi vị trí của sinh viên
        resultIntent.putExtra("position", position)
      }

      setResult(RESULT_OK, resultIntent)
      finish()
    }
  }
}
