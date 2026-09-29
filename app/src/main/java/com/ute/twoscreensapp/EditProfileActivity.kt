package com.ute.twoscreensapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ute.twoscreensapp.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Nhận dữ liệu truyền từ MainActivity sang
        val student = intent.getSerializableExtra("EXTRA_STUDENT") as? Student
        student?.let {
            binding.edtName.setText(it.name)
            binding.edtGpa.setText(it.gpa.toString())
        }

        // 2. Xử lý nút Lưu (Trả dữ liệu về Màn hình 1)
        binding.btnSave.setOnClickListener {
            val name = binding.edtName.text.toString().trim()
            val gpa = binding.edtGpa.text.toString().toDoubleOrNull()

            if (name.isEmpty() || gpa == null || gpa !in 0.0..4.0) {
                Toast.makeText(this, "Vui lòng nhập Tên và GPA hợp lệ!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val updatedStudent = student?.copy(name = name, gpa = gpa)

            val resultIntent = Intent().apply {
                putExtra("EXTRA_UPDATED_STUDENT", updatedStudent)
            }

            // Gán kết quả OK và đóng màn hình
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        // 3. Xử lý nút Hủy (Quay về mà không lưu)
        binding.btnCancel.setOnClickListener {
            finish() // Không gọi setResult, mặc định sẽ là RESULT_CANCELED
        }
    }
}