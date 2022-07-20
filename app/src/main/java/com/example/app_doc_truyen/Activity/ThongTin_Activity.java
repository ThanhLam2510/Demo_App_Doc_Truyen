package com.example.app_doc_truyen.Activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ThongTin_Activity extends AppCompatActivity {
    private LinearLayout linearperson;
    private LinearLayout linearchangepass;
    private LinearLayout linearlogout;
    private Switch aSwitch;
    private ImageView UploadIMGuser;
    private TextView tvtenuser;
    private TextView tvemailuser;
    private TextView tvsdtuser;
    User users;
    ArrayList<User> UserArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
      check();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin);
        aSwitch=findViewById(R.id.designbottom);
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            aSwitch.setChecked(true);
        }else {
            aSwitch.setChecked(false);
        }
        linearperson = findViewById(R.id.linearperson);
        linearchangepass = findViewById(R.id.linearchangepass);
        linearlogout = findViewById(R.id.linearlogout);

        UploadIMGuser = findViewById(R.id.UploadIMGuser);
        tvtenuser = findViewById(R.id.tvtenuser);
        tvemailuser = findViewById(R.id.tvemailuser);
        tvsdtuser = findViewById(R.id.tvsdtuser);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });
        //thogtincanhan
        linearperson.setOnClickListener(v -> {
                Intent intent=new Intent(this, Chitet_user.class);
            intent.putExtra("IDuser", users.getIDuser());
            intent.putExtra("Name", users.getName());
            intent.putExtra("Avartar", users.getAvartar());
            intent.putExtra("DateOfBirth", users.getDateOfBirth());
            intent.putExtra("SDT", users.getSDT());
            intent.putExtra("GioiTinh", users.getGioiTinh());
            intent.putExtra("Email", users.getEmail());
                startActivity(intent);
        });
        //doi pass
        linearchangepass.setOnClickListener(v -> {
            Intent intent=new Intent(this, ChangepassActivity.class);
            startActivity(intent);
        });
        getdata();
        //dang xuat
        linearlogout.setOnClickListener(v -> {
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("Đăng Xuất.");
            builder.setMessage("Bạn Có Muốn Thoát Ứng Dụng Không ?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent=new Intent(getApplication(),Login_Activity.class);
                    startActivity(intent);
//                    System.exit(0 );
                    finish();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
        });
    }


    public void check(){
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.Theme_Dark);
        }else {
            setTheme(R.style.Theme_Light);
        }
    }
    private void getdata(){
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("user_file", Context.MODE_PRIVATE);
        String user = preferences.getString("gmail", "");
        String matkhau = preferences.getString("matkhau", "");
        StringRequest request = new StringRequest(Request.Method.POST, "https://myappnhom5.000webhostapp.com/appdoctruyen/getAuthor.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    UserArrayList = new ArrayList<>();
                    JSONObject jsonObject = new JSONObject(response);
                    String result = jsonObject.getString("status");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    if(result.equals("thanh cong")){
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject object=jsonArray.getJSONObject(i);
                            UserArrayList.add(new User(
                                    object.getInt("IDuser"),
                                    object.getString("Name"),
                                    object.getString("Avatar"),
                                    object.getString("DateOfBirth"),
                                    object.getString("SDT"),
                                    object.getString("GioiTinh"),
                                    object.getString("Email"),
                                    object.getString("Pass")
                            ));
                            users=UserArrayList.get(0);
                            tvsdtuser.setText(users.getSDT());
                            tvemailuser.setText(users.getEmail());
                            tvtenuser.setText(users.getName());
                            Picasso.get().load(users.getAvartar())
                                    .into(UploadIMGuser);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "xảy ra lỗi!", Toast.LENGTH_SHORT).show();
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Email", user);
                params.put("Pass", matkhau);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }
}