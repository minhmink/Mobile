package com.example.tlucontact;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tlucontact.models.Contact;
import com.example.tlucontact.models.Employee;
import com.example.tlucontact.models.Unit;

public class DetailActivity extends AppCompatActivity {
    private ImageView imvAvatar;
    private TextView txtName, txtPhone, txtEmail, txtUnitAddress, txtPosition;
    private Button btnBack;
    private ImageButton btnPhone, btnSms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.include_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Chi tiết");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        imvAvatar = (ImageView) findViewById(R.id.imv_avatar);
        txtName = (TextView) findViewById(R.id.txt_name);
        txtPhone = (TextView) findViewById(R.id.txt_phone);
        txtEmail = (TextView) findViewById(R.id.txt_email);
        txtUnitAddress = (TextView) findViewById(R.id.txt_unit_address);
        txtPosition = (TextView) findViewById(R.id.txt_position);
        btnPhone = (ImageButton) findViewById(R.id.btn_phone);
        btnSms = (ImageButton) findViewById(R.id.btn_sms);

        Intent intent = getIntent();
        Contact contact = (Contact) intent.getSerializableExtra("contact");
        if (contact != null) {
            imvAvatar.setImageResource(contact.getAvatar());
            txtName.setText(contact.getName());
            txtPhone.setText("Số điện thoại: " + contact.getPhone());


            if (contact instanceof Employee) {
                Employee employee = (Employee) contact;
                txtEmail.setText("Email: " + employee.getEmail());
                txtUnitAddress.setText("Khoa: " + employee.getUnit());
                txtPosition.setText("Chức vụ: " + employee.getPosition());

                txtEmail.setVisibility(View.VISIBLE);
                txtPosition.setVisibility(View.VISIBLE);
            } else if (contact instanceof Unit) {
                Unit unit = (Unit) contact;
                txtUnitAddress.setText("Địa chỉ: " + unit.getAddress());
            }
        }
        btnPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(android.net.Uri.parse("tel:" + contact.getPhone()));
                startActivity(intent);
            }
        });
        btnSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(android.net.Uri.parse("sms:" + contact.getPhone()));
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        String type = "";
        Contact contact = (Contact) getIntent().getSerializableExtra("contact");
        if (contact instanceof Employee) {
            type = "employee";
        } else {
            type = "unit";
        }
        Intent resultIntent = new Intent();
        resultIntent.putExtra("type", type);
        setResult(RESULT_OK, resultIntent);
        finish();
        return true;
    }


}