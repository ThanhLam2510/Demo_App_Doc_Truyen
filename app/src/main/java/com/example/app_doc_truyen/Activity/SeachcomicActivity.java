package com.example.app_doc_truyen.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
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
import com.example.app_doc_truyen.Model.Comic;
import com.example.demo_app_doc_truyen.R;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SeachcomicActivity extends AppCompatActivity {
    ImageButton imgbtseach;
    TextInputEditText comic;
    RecyclerView rclTimKiem;
    ArrayList<Comic> ComicArrayList;
    Adapter_seachcomic adapterseachcomic;
    String timKiem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seachcomic);
        imgbtseach=findViewById(R.id.imgseachSP);
        comic=findViewById(R.id.edtseachSP);
        rclTimKiem=findViewById(R.id.rclTimKiemSP);


        ComicArrayList = new ArrayList<>();
        adapterseachcomic = new Adapter_seachcomic(ComicArrayList, getApplicationContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        rclTimKiem.setLayoutManager(linearLayoutManager);
        rclTimKiem.setAdapter(adapterseachcomic);
        imgbtseach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ComicArrayList.clear();
                SeachSP();
            }
        });
    }
    private void SeachSP(){
        timKiem = comic.getText().toString().trim();
        if(!validate()  ){
            return;
        }else {
            postSeach();
        }
    }
    private void postSeach() {
        final ProgressDialog progressDialog = new ProgressDialog(SeachcomicActivity.this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(SeachcomicActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://myappnhom5.000webhostapp.com/appdoctruyen/Seachcm.php", new Response.Listener<String>() {
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
                            ComicArrayList.add(new Comic(
                                            object.getInt("IDcomic"),
                                            object.getInt("IDTL"),
                                            object.getString("NameCM"),
                                            object.getString("Content"),
                                            object.getString("ImgCM"),
                                            object.getString("Author"),
                                            object.getString("Favourite"),
                                            object.getString("Date")
                            ));
                            adapterseachcomic.notifyDataSetChanged();
                        }
                    } else {
                        adapterseachcomic.notifyDataSetChanged();
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
                params.put("NameCM", timKiem);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    public boolean validate(){
        if(timKiem.equalsIgnoreCase("")){
            comic.setError("Nhập để tìm kiếm.");
            return false;
        }else{
            comic.setError(null);
            return true;
        }
    }
}