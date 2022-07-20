package com.example.app_doc_truyen.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_doc_truyen.Model.Comic;
import com.example.app_doc_truyen.Model.User;
import com.example.demo_app_doc_truyen.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Chitet_user extends AppCompatActivity {
    private ImageView imgpersonup;
    private TextView Name;
    private TextView email;
    private TextView tvGioitinh;
    private TextView tcNgaysinh;
    private TextView tvSdt;
    private Button btncancle;
    private Button btnSuas;

    int id;
    String Names,Avartar,DateOfBirth,SDT,Email,GioiTinh;
    Intent intent;
    ArrayList<Comic> postList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitet_user);
        //
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            getApplication().setTheme(R.style.Theme_Dark);
        }else {
            getApplication().setTheme(R.style.Theme_Light);
        }
        //
        imgpersonup = findViewById(R.id.imgpersonup);
        Name = findViewById(R.id.Name);
        email = findViewById(R.id.email);
        tvGioitinh = findViewById(R.id.tv_gioitinh);
        tcNgaysinh = findViewById(R.id.tc_ngaysinh);
        tvSdt = findViewById(R.id.tv_sdt);
        btncancle = findViewById(R.id.btncancle);
        btnSuas = findViewById(R.id.btn_suas);
        //
        id=getIntent().getIntExtra("IDuser",0);
        Names = getIntent().getStringExtra("Name");
        Avartar = getIntent().getStringExtra("Avartar");
        DateOfBirth = getIntent().getStringExtra("DateOfBirth");
        SDT = getIntent().getStringExtra("SDT");
        Email = getIntent().getStringExtra("Email");
        GioiTinh = getIntent().getStringExtra("GioiTinh");
        intent = getIntent();
        //
        Picasso.get().load(Avartar)
                .into(imgpersonup);
        Name.setText(Names);
        email.setText("Email: "+Email);
        tvGioitinh.setText("Giới tính: "+GioiTinh);
        tcNgaysinh.setText("Ngày sinh: "+DateOfBirth);
        tvSdt.setText("Số điện thoại: "+SDT);
        btnSuas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplication(),Sua_thongtin.class);
                intent.putExtra("IDuser", id);
                intent.putExtra("Name", Names);
                intent.putExtra("Avartar", Avartar);
                intent.putExtra("DateOfBirth", DateOfBirth);
                intent.putExtra("SDT", SDT);
                intent.putExtra("Email", Email);
                intent.putExtra("GioiTinh", GioiTinh);
                startActivity(intent);
            }
        });
        btncancle.setOnClickListener(v -> {
            onBackPressed();
        });
    }
 }




