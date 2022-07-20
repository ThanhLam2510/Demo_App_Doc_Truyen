package com.example.app_doc_truyen.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_doc_truyen.Activity.MainActivity;
import com.example.app_doc_truyen.Activity.ThongTin_Activity;
import com.example.app_doc_truyen.Adapter.Adapter_baiviet;
import com.example.app_doc_truyen.Adapter.Adapter_quanlybaiviet;
import com.example.app_doc_truyen.Adapter.TapAdapter;
import com.example.app_doc_truyen.Model.Comic;
import com.example.app_doc_truyen.Model.User;
import com.example.demo_app_doc_truyen.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CaNhan_Fragment extends Fragment {
    ImageView iconThongTin;
    TextView edtTen_Nd, edtmail_Nd;
    ImageView imganhavtar;
    ArrayList<User> UserArrayList;
    User users;
    TextInputEditText name, anh, description;
    Button btn_addtruyen;
    RecyclerView recy;
    Adapter_quanlybaiviet adapter;
    ArrayList<Comic> list;
    FloatingActionButton btnfloat;
    String user, matkhau;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ca_nhan_, container, false);
        edtTen_Nd = view.findViewById(R.id.edtTen_Nd);
        edtmail_Nd = view.findViewById(R.id.edtmail_Nd);
        iconThongTin = view.findViewById(R.id.iconThongTin);
        imganhavtar = view.findViewById(R.id.imganhavtar);
        btnfloat = view.findViewById(R.id.fab_themnhanvien);
        recy = view.findViewById(R.id.recy);
        list = new ArrayList<>();
        recy.setHasFixedSize(true);
        recy.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new Adapter_quanlybaiviet(list, getContext());
        recy.setAdapter(adapter);
        SharedPreferences preferences = getActivity().getSharedPreferences("user_file", Context.MODE_PRIVATE);
        user = preferences.getString("gmail", "");
        matkhau = preferences.getString("matkhau", "");
        btnfloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
                View view1 = LayoutInflater.from(getContext()).inflate(R.layout.dialog_addtruyen, null);
                builder.setView(view1);
                AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                alertDialog.show();
//                mAPIService = ApiUtils.getAPIService();
                // ánh xạ
                name = view1.findViewById(R.id.tentruyen_addtruyen);
                anh = view1.findViewById(R.id.img_addtruyen);
                description = view1.findViewById(R.id.mota_addtruyen);

                btn_addtruyen = view1.findViewById(R.id.bnt_add_truyen);
                //
                DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                String date = df.format(Calendar.getInstance().getTime());
                String Favourite = "False";
                btn_addtruyen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String Name = name.getText().toString().trim();
                        String anhs = anh.getText().toString().trim();
                        String Description = description.getText().toString().trim();
                        if (!validatehinhanh() | !validatehinhanh1()| !validatehinhanh2()) {
                            return;
                        } else {
                            RequestQueue requestQueue = Volley.newRequestQueue(builder.getContext());
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://myappnhom5.000webhostapp.com/appdoctruyen/themtruyen.php", new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if (response.equalsIgnoreCase("Thêm Thành Công")) {
                                        Toast.makeText(builder.getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(builder.getContext(), " Thêm không Thành công!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(builder.getContext(), "Xảy ra lỗi", Toast.LENGTH_SHORT).show();
                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<String, String>();
                                    Log.e("ahaahhaha", Name + Description + anhs);
                                    params.put("Email", user);
                                    params.put("Pass", matkhau);
                                    params.put("NameCM", Name);
                                    params.put("ImgCM", anhs);
                                    params.put("Content", Description);
                                    params.put("Date", date);
                                    params.put("Favourite", Favourite);
                                    return params;
                                }
                            };

                            requestQueue.add(stringRequest);
                            alertDialog.dismiss();
                        }
                    }
                });

            }
        });
        getdata1();

        getdata();
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            getActivity().setTheme(R.style.Theme_Dark);
//            ThongTin_Activity.check(true);
        } else {
            getActivity().setTheme(R.style.Theme_Light);
//            ThongTin_Activity.check(false);
        }
        iconThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ThongTin_Activity.class);
                startActivity(intent);
            }
        });
        getdata();
        return view;
    }

    private void getdata() {
        SharedPreferences preferences = getActivity().getSharedPreferences("user_file", Context.MODE_PRIVATE);
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
                    if (result.equals("thanh cong")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
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
                            users = UserArrayList.get(0);
                            edtmail_Nd.setText(users.getEmail());
                            edtTen_Nd.setText(users.getName());
                            Picasso.get().load(users.getAvartar())
                                    .into(imganhavtar);
                        }
                    } else {
                        Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "xảy ra lỗi!", Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);
    }

    private void getdata1() {
        StringRequest request = new StringRequest(Request.Method.POST, "https://myappnhom5.000webhostapp.com/appdoctruyen/listcomictacgia.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String result = jsonObject.getString("status");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    if (result.equals("thanh cong")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            list.add(new Comic(
                                    object.getInt("IDcomic"),
                                    object.getInt("IDTL"),
                                    object.getString("NameCM"),
                                    object.getString("Content"),
                                    object.getString("ImgCM"),
                                    object.getString("Author"),
                                    object.getString("Favourite"),
                                    object.getString("Date")
                            ));
                            Comic comic = list.get(0);
                            Log.e("tentacia", comic.getNameCM());
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "xảy ra lỗi!", Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);
    }
    public boolean validatehinhanh(){
        if(name.getText().toString().equals("")){
            name.setError("Không được bỏ trống.");
            return false;
        } else{
            name.setError(null);
            return true;
        }
    }
    public boolean validatehinhanh1(){
        if(description.getText().toString().equals("")){
            description.setError("Không được bỏ trống.");
            return false;
        } else{
            description.setError(null);
            return true;
        }
    }
    public boolean validatehinhanh2(){
        if(anh.getText().toString().equals("")){
            anh.setError("Không được bỏ trống.");
            return false;
        } else{
            anh.setError(null);
            return true;
        }
    }
}