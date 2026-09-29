package com.ute.twoscreensapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.ute.twoscreensapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentStudent = Student("SV01", "Nguyen Van A", 3.5)

    // 1. Đăng ký Launcher ở cấp thuộc tính lớp
    private lateinit var editProfileLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        updateUI()

        // 2. Khởi tạo Launcher trong onCreate() [Bắt buộc trước khi RESUMED]
        editProfileLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            // Khi Màn hình 2 đóng và trả kết quả về
            if (result.resultCode == Activity.RESULT_OK) {
                val updatedStudent = result.data?.getSerializableExtra("EXTRA_UPDATED_STUDENT") as? Student
                updatedStudent?.let {
                    currentStudent = it
                    updateUI()
                    Toast.makeText(this, "Đã cập nhật thông tin thành công!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // 3. Sự kiện bấm nút chuyển sang Màn hình 2
        binding.btnOpenEdit.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java).apply {
                putExtra("EXTRA_STUDENT", currentStudent)
            }
            editProfileLauncher.launch(intent)
        }
    }

    private fun updateUI() {
        binding.tvStudentInfo.text = "Tên: ${currentStudent.name}\nGPA: ${currentStudent.gpa}"
    }
}