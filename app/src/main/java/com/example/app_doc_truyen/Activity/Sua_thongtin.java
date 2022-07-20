package com.example.app_doc_truyen.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
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
import com.example.demo_app_doc_truyen.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

public class Sua_thongtin extends AppCompatActivity {
    private TextInputEditText edtNameSua;
    private TextInputEditText edtimgSua;
    private TextInputEditText edtSDTSua;
    private TextInputEditText edtEmailSua;
    private TextInputEditText edtDate;
    private Button btnsuauser;
    private Button btnhuyuser;
    Intent intent;

    int id;
    String Names,Avartar,DateOfBirth,SDT,Email;
    String Namesup,Avartarup,DateOfBirthup,SDTup,Emailup,mailcu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_thongtin);
        edtNameSua = findViewById(R.id.edtName_sua);
        edtimgSua = findViewById(R.id.edtimg_sua);
        edtSDTSua = findViewById(R.id.edtSDT_sua);
        edtEmailSua = findViewById(R.id.edtEmail_sua);
        edtDate = findViewById(R.id.edtDate);
        btnsuauser = findViewById(R.id.btnsuauser);
        btnhuyuser = findViewById(R.id.btnhuyuser);
        id=getIntent().getIntExtra("IDuser",0);
        Names = getIntent().getStringExtra("Name");
        Avartar = getIntent().getStringExtra("Avartar");
        DateOfBirth = getIntent().getStringExtra("DateOfBirth");
        SDT = getIntent().getStringExtra("SDT");
        Email = getIntent().getStringExtra("Email");
        intent = getIntent();
        edtNameSua.setText(Names);
        edtimgSua.setText(Avartar);
        edtSDTSua.setText(SDT);
        edtEmailSua.setText(Email);
        edtDate.setText(DateOfBirth);

        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            getApplication().setTheme(R.style.Theme_Dark);
        }else {
            getApplication().setTheme(R.style.Theme_Light);
        }
        btnhuyuser.setOnClickListener(v -> {
            onBackPressed();
        });
        btnsuauser.setOnClickListener(v -> {
            final ProgressDialog progressDialog = new ProgressDialog( Sua_thongtin.this);
            progressDialog.setMessage("Please Wait..");
            Namesup = edtNameSua.getText().toString();
            Avartarup = edtimgSua.getText().toString();
            DateOfBirthup = edtDate.getText().toString();
            SDTup = edtSDTSua.getText().toString();
            Emailup = edtEmailSua.getText().toString();
            mailcu = Email;
            if (!validatename() | !validateemail() | !validatesdt() | !validateanh() | !validatedate()) {
                return;
            } else {
                progressDialog.show();
                StringRequest request = new StringRequest(Request.Method.POST, "https://myappnhom5.000webhostapp.com/appdoctruyen/updateuser.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Toast.makeText(Sua_thongtin.this, response, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(Sua_thongtin.this, "xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Email", Emailup);
                        params.put("Name", Namesup);
                        params.put("Avatar", Avartarup);
                        params.put("DateOfBirth", DateOfBirthup);
                        params.put("SDT", SDTup);
                        params.put("mailcu", mailcu);
                        return params;

                    }
                };
                RequestQueue requestQue = Volley.newRequestQueue(Sua_thongtin.this);
                requestQue.add(request);
            }
        });
    }
    public boolean validatename(){
        if(edtNameSua.getText().toString().equals("")){
            edtNameSua.setError("Hãy nhập tên của bạn.");
            return false;
        } else{
            edtNameSua.setError(null);
            return true;
        }
    }
    public boolean validateanh(){
        if(edtimgSua.getText().toString().equals("")){
            edtimgSua.setError("Hãy nhập ảnh của bạn.");
            return false;
        } else{
            edtimgSua.setError(null);
            return true;
        }
    }
    public boolean validatedate(){
        if(edtDate.getText().toString().equals("")){
            edtDate.setError("Hãy nhập năm sinh của bạn.");
            return false;
        } else{
            edtDate.setError(null);
            return true;
        }
    }
    public boolean validateemail(){
        String a="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(edtEmailSua.getText().toString().equals("")){
            edtEmailSua.setError("Hãy nhập gmail của bạn.");
            return false;
        }else if(!edtEmailSua.getText().toString().matches(a)) {
            edtEmailSua.setError("Nhập đúng định dạng gmail.");
            return false;
        }else{
            edtEmailSua.setError(null);
            return true;
        }
    }
    public boolean validatesdt(){
        String a = "^0[0-9]{9}$";
        if(edtSDTSua.getText().toString().equals("")){
            edtSDTSua.setError("Nhập số điện thoại của bạn");
            return false;
        }else if(!edtSDTSua.getText().toString().matches(a)){
            edtSDTSua.setError("Hãy nhập đúng định dạng số điện thoại!");
            return false;
        }
        else{
            edtSDTSua.setError(null);
            return true;
        }
    }
}