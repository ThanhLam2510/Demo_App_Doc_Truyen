package com.example.app_doc_truyen.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_doc_truyen.Adapter.Adapter_baiviet;
import com.example.app_doc_truyen.Adapter.Adapter_seachcomic;
import com.example.app_doc_truyen.Adapter.Adapter_theloai;
import com.example.app_doc_truyen.Model.Comic;
import com.example.app_doc_truyen.Model.TLcomic;
import com.example.demo_app_doc_truyen.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class listTLActivity extends AppCompatActivity {
    RecyclerView rcllisttl;
    ArrayList<Comic> comicArrayList;
    Adapter_seachcomic adapter_seachcomic;
    int value2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tlactivity);
        rcllisttl=findViewById(R.id.rcllisttl);
        rcllisttl.setHasFixedSize(true);
        rcllisttl.setLayoutManager(new LinearLayoutManager(listTLActivity.this));
        comicArrayList = new ArrayList<>();
        adapter_seachcomic = new Adapter_seachcomic(comicArrayList, listTLActivity.this);
        rcllisttl.setAdapter(adapter_seachcomic);
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            listTLActivity.this.setTheme(R.style.Theme_Dark);
        }else {
            listTLActivity.this.setTheme(R.style.Theme_Light);
        }
        Intent intent = getIntent();
        value2 = intent.getIntExtra("Key_2", 0);
        Log.e("ahi", String.valueOf(value2));
//        SanPham LoaiCafe = (SanPham) getIntent().getSerializableExtra("data2");
        getPK();
    }

    private void getPK() {
                final ProgressDialog progressDialog = new ProgressDialog(listTLActivity.this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://myappnhom5.000webhostapp.com/appdoctruyen/phanchiaTL.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressDialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response);
                    String result = jsonObject.getString("status");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    if (result.equals("thanh cong")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            comicArrayList.add(new Comic(
                                    object.getInt("IDcomic"),
                                    object.getInt("IDTL"),
                                    object.getString("NameCM"),
                                    object.getString("Content"),
                                    object.getString("ImgCM"),
                                    object.getString("Author"),
                                    object.getString("Favourite"),
                                    object.getString("Date")
                            ));
                            Comic comic=comicArrayList.get(0);
                            Log.e("ahiihi",comic.getNameCM());
                            adapter_seachcomic.notifyDataSetChanged();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Không tìm thấy sản nào phù hợp.", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "xảy ra lỗi!", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("IDTL", String.valueOf(value2));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}