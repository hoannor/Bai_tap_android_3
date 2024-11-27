package vn.edu.hust.activityexamples

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
  private val students = mutableListOf<String>()  // Danh sách sinh viên
  private lateinit var listView: ListView
  private lateinit var studentAdapter: ArrayAdapter<String>

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    listView = findViewById(R.id.listView)
    studentAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, students)
    listView.adapter = studentAdapter

    // Đăng ký context menu cho ListView
    registerForContextMenu(listView)

    // Mở AddStudentActivity để thêm sinh viên mới
    findViewById<Button>(R.id.button_add_student).setOnClickListener {
      val intent = Intent(this, AddStudentActivity::class.java)
      startActivityForResult(intent, ADD_STUDENT_REQUEST_CODE)
    }
  }

  // Nhận kết quả trả về từ AddStudentActivity
  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (resultCode == RESULT_OK) {
      val hoten = data?.getStringExtra("hoten") ?: ""
      val mssv = data?.getStringExtra("mssv") ?: ""
      if (requestCode == ADD_STUDENT_REQUEST_CODE) {
        // Thêm sinh viên mới
        students.add("$hoten - $mssv")
        studentAdapter.notifyDataSetChanged()  // Cập nhật ListView
      } else if (requestCode == EDIT_STUDENT_REQUEST_CODE) {
        // Chỉnh sửa sinh viên
        val position = data?.getIntExtra("position", -1) ?: -1
        if (position != -1) {
          students[position] = "$hoten - $mssv"
          studentAdapter.notifyDataSetChanged()  // Cập nhật ListView
        }
      }
    }
  }


  // Thiết lập OptionMenu
  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.main_menu, menu)
    return super.onCreateOptionsMenu(menu)
  }

  // Xử lý sự kiện chọn mục trong OptionMenu
  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.action_add_student -> {
        val intent = Intent(this, AddStudentActivity::class.java)
        startActivityForResult(intent, ADD_STUDENT_REQUEST_CODE)
        return true
      }
      else -> return super.onOptionsItemSelected(item)
    }
  }

  // Thiết lập ContextMenu
  override fun onCreateContextMenu(
    menu: ContextMenu?,
    v: View?,
    menuInfo: ContextMenu.ContextMenuInfo?
  ) {
    super.onCreateContextMenu(menu, v, menuInfo)
    menuInflater.inflate(R.menu.context_menu, menu)
  }

  // Xử lý sự kiện chọn mục trong ContextMenu
  override fun onContextItemSelected(item: MenuItem): Boolean {
    val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
    val position = info.position
    when (item.itemId) {
      R.id.action_edit -> {
        // Mở activity để sửa sinh viên
        val intent = Intent(this, AddStudentActivity::class.java)
        val studentData = students[position].split(" - ")
        intent.putExtra("hoten", studentData[0])
        intent.putExtra("mssv", studentData[1])
        intent.putExtra("position", position)  // Truyền vị trí của sinh viên
        startActivityForResult(intent, EDIT_STUDENT_REQUEST_CODE)
        return true
      }
      R.id.action_remove -> {
        students.removeAt(position)  // Xóa sinh viên khỏi danh sách
        studentAdapter.notifyDataSetChanged()  // Cập nhật ListView
        Toast.makeText(this, "Student removed", Toast.LENGTH_SHORT).show()
        return true
      }
      else -> return super.onContextItemSelected(item)
    }
  }

  companion object {
    private const val ADD_STUDENT_REQUEST_CODE = 1
    private const val EDIT_STUDENT_REQUEST_CODE = 2
  }
}
