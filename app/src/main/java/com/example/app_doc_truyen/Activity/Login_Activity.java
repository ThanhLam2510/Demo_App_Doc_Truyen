package com.example.app_doc_truyen.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login_Activity extends AppCompatActivity {
     TextInputEditText txtUsername ,txtpast  ;
    private CheckBox checkRemeber;
    private Button btnDangnhap;
    private Button btnDangky;
    String str_email,str_password;
    String token = null ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("ĐĂNG NHẬP");
        setContentView(R.layout.activity_login);
        txtpast = findViewById(R.id.edtPass) ;
        txtUsername = findViewById(R.id.edtUser);
        btnDangnhap = (Button) findViewById(R.id.btnDangNhap);
        checkRemeber = (CheckBox) findViewById(R.id.checkBox);
        btnDangky=(Button) findViewById(R.id.btnDangKy);
        SharedPreferences preferences =getSharedPreferences("user_file", MODE_PRIVATE);
        txtUsername.setText(preferences.getString("gmail",""));
        txtpast.setText(preferences.getString("matkhau",""));
        checkRemeber.setChecked(preferences.getBoolean("remember",false));
        btnDangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateemail() | !validatepass() ){
                    return;
                }
                else {
                    postLogin();
//                    startActivity(new Intent(Login_Activity.this, MainActivity.class));
                }

            }
        });
        btnDangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(),DangKy_Activity.class);
                startActivity(intent);
            }
        });
    }
   private void postLogin() {
       final ProgressDialog progressDialog = new ProgressDialog(Login_Activity.this);
       progressDialog.setMessage("Please Wait..");
       progressDialog.show();
       str_email = txtUsername.getText().toString().trim();
       str_password = txtpast.getText().toString().trim();
       StringRequest request = new StringRequest(Request.Method.POST, "https://myappnhom5.000webhostapp.com/appdoctruyen/Login.php", new com.android.volley.Response.Listener<String>() {
           @Override
           public void onResponse(String response) {
               progressDialog.dismiss();
               if(response.equalsIgnoreCase("Đăng Nhập Thành Công")){
                   remember(str_email,str_password, checkRemeber.isChecked());
                   Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                   intent.putExtra("user",str_email);
                   startActivity(intent);
                   Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
               }
               else{
                   Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
               }

           }
       },new Response.ErrorListener(){

           @Override
           public void onErrorResponse(VolleyError error) {
               progressDialog.dismiss();
               Toast.makeText(getApplicationContext(), "xảy ra lỗi!", Toast.LENGTH_SHORT).show();
           }
       }

       ){
           @Override
           protected Map<String, String> getParams() throws AuthFailureError {
               Map<String,String> params = new HashMap<String, String>();
               params.put("Email",str_email);
               params.put("Pass",str_password);
               return params;

           }
       };

       RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
       requestQueue.add(request);
    }
    public boolean validateemail(){
        String a="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(txtUsername.getText().toString().equals("")){
            txtUsername.setError("Hãy nhập gmail của bạn.");
            return false;
        }else if(!txtUsername.getText().toString().matches(a)) {
            txtUsername.setError("Nhập đúng định dạng gmail.");
            return false;
        }else{
            txtUsername.setError(null);
            return true;
        }
    }
    private void remember(String strname, String strpass, boolean checked) {
        SharedPreferences preferences=getSharedPreferences("user_file",MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        if(!checked){
            editor.clear();
        }else {
            editor.putString("gmail",strname);
            editor.putString("matkhau",strpass);
            editor.putBoolean("remember",checked);
        }
        editor.commit();
    }
    public boolean validatepass(){
        if(txtpast.getText().toString().equals("")){
            txtpast.setError("Nhập mật khẩu của bạn");
            return false;
        } else{
            txtpast.setError(null);
            return true;
        }
    }
}