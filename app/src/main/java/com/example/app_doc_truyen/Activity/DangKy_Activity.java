package com.example.app_doc_truyen.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_doc_truyen.Model.User;
import com.example.demo_app_doc_truyen.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DangKy_Activity extends AppCompatActivity {
    TextInputEditText DateFormat;
    TextInputEditText edtSDT, edtEmail, edtHoTen, edtMk, edtMkck,edtDate;
    Button btnDK, btnHuy;
    RadioButton rbNam , rbNu;
    RadioGroup radioGroup;
    DatePickerDialog.OnDateSetListener onDateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        setTitle("Dang Ky");
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DateFormat=findViewById(R.id.DateFormat);
//        edtDate= findViewById(R.id.edtDate);
        edtEmail = findViewById(R.id.edtEmail);
        edtHoTen = findViewById(R.id.edtHoTen);
        edtMk = findViewById(R.id.edtPassDk);
        edtMkck = findViewById(R.id.edtPassCk);
        edtSDT = findViewById(R.id.edtSDT);
        btnDK = findViewById(R.id.btnDangKyT);
        btnHuy = findViewById(R.id.btnHuy);
        radioGroup = findViewById(R.id.radioG);
        rbNam =findViewById(R.id.rbNam);
        rbNu=findViewById(R.id.rbNu);


        btnDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validatename() | !validatesdt() | !validateemail() | !validatepass()|!validatrepass() ){
                    return;
                }
                else{
                    postData ();
                }
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login_Activity.class);
                startActivity(intent);
            }
        });
        DateFormat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        DangKy_Activity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        onDateSetListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                datePickerDialog.show();

            }
        });
        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month =month+1;
                String date = dayOfMonth+"/"+month+"/"+year;
                DateFormat.setText(date);
            }
        };
    }




    private void postData ()
    {
        final ProgressDialog progressDialog = new ProgressDialog(DangKy_Activity.this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.show();
        String FullName = edtHoTen.getText().toString() ;

        String Password = edtMk.getText().toString() ;
        String mkcheck = edtMkck.getText().toString() ;
        String PhoneNumber = edtSDT.getText().toString() ;
        String DateOfBirth = DateFormat.getText().toString() ;
        String Email  = edtEmail.getText().toString() ;
        String Gt ;
        if (rbNam.isChecked()== true)
        {
            Gt =  "nam " ;
        }
        else
        {
            Gt = "nữ" ;
        }
        StringRequest request = new StringRequest(Request.Method.POST, "https://myappnhom5.000webhostapp.com/appdoctruyen/Register.php", new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                if(response.equalsIgnoreCase("Đăng kí Thành Công")){
                    edtHoTen.setText("");
                    edtMk.setText("");
                    edtMkck.setText("");
                    edtSDT.setText("");
                    DateFormat.setText("");
                    edtEmail.setText("");
                    Toast.makeText(getApplication(), response, Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplication(), response, Toast.LENGTH_SHORT).show();
                }
            }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplication(), "xảy ra lỗi!", Toast.LENGTH_SHORT).show();
            }}) {@Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String,String> params = new HashMap<String, String>();
            params.put("Name",FullName);
            params.put("Pass",Password);
//            params.put("mkcheck",mkcheck);
            params.put("SDT",PhoneNumber);
            params.put("DateOfBirth",DateOfBirth);
            params.put("Email",Email);
            params.put("GioiTinh","Nam");
            return params;

        }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplication());
        requestQueue.add(request);
    }
    public boolean validatename(){
        if(edtHoTen.getText().toString().equals("")){
            edtHoTen.setError("Hãy nhập tên của bạn.");
            return false;
        } else{
            edtHoTen.setError(null);
            return true;
        }
    }
    public boolean validateemail(){
        String a="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(edtEmail.getText().toString().equals("")){
            edtEmail.setError("Hãy nhập gmail của bạn.");
            return false;
        }else if(!edtEmail.getText().toString().matches(a)) {
            edtEmail.setError("Nhập đúng định dạng gmail.");
            return false;
        }else{
            edtEmail.setError(null);
            return true;
        }
    }
    public boolean validatrepass(){
        if(edtMkck.getText().toString().equals("")){
            edtMkck.setError("Nhập mật khẩu của bạn");
            return false;
        }else if(!edtMkck.getText().toString().matches(edtMk.getText().toString())) {
            Log.e("aa",edtMkck.getText().toString()+edtMk.getText().toString() );
            edtMkck.setError("Mật khẩu không trùng khớp.");
            return false;
        }
        else{
            edtMkck.setError(null);
            return true;
        }
    }
    public boolean validatepass(){
        if(edtMk.getText().toString().equals("")){
            edtMk.setError("Nhập mật khẩu của bạn");
            return false;
        }else if(edtMk.length()<8) {
            edtMk.setError("Nhập mật khẩu trên 8 kí tự.");
            return false;
        }
        else{
            edtMk.setError(null);
            return true;
        }
    }
    public boolean validatesdt(){
        String a = "^0[0-9]{9}$";
        if(edtSDT.getText().toString().equals("")){
            edtSDT.setError("Nhập số điện thoại của bạn");
            return false;
        }else if(!edtSDT.getText().toString().matches(a)){
            edtSDT.setError("Hãy nhập đúng định dạng số điện thoại!");
            return false;
        }
        else{
            edtSDT.setError(null);
            return true;
        }
    }

}
