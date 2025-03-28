package com.example.bt1

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.sharedpreference.PreferenceHelper

class MainActivity : ComponentActivity() {
    // Khai báo biến
    private lateinit var edtUserName: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnSave: Button
    private lateinit var btnDelete: Button
    private lateinit var btnShow: Button
    private lateinit var txtResult: TextView
    private lateinit var preferenceHelper: PreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Gán giá trị
        edtUserName = findViewById(R.id.edt_user_name)
        edtPassword = findViewById(R.id.edt_password)
        btnSave = findViewById(R.id.btn_Save)
        btnDelete = findViewById(R.id.btn_Delete)
        btnShow = findViewById(R.id.btn_Show)
        txtResult = findViewById(R.id.txt_result)

        // Khởi tạo SharedPreferences
        preferenceHelper = PreferenceHelper(this)

        btnSave.setOnClickListener {
            val username = edtUserName.text.toString()
            val password = edtPassword.text.toString()
            preferenceHelper.saveUser(username, password)
            Toast.makeText(this, "Đã lưu thông tin!", Toast.LENGTH_SHORT).show()
            edtUserName.text.clear()
            edtPassword.text.clear()
        }

        btnDelete.setOnClickListener {
            preferenceHelper.clearData()
            txtResult.text = "Dữ liệu đã bị xóa!"
            Toast.makeText(this, "Đã xóa dữ liệu!", Toast.LENGTH_SHORT).show()
        }

        btnShow.setOnClickListener {
            val username = preferenceHelper.getUsername()
            val password = preferenceHelper.getPassword()
            txtResult.text = "Tên: ${username ?: "Không có dữ liệu"}\nMật khẩu: ${password ?: "Không có dữ liệu"}"
        }
    }
}
