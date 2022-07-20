package com.example.app_doc_truyen.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_doc_truyen.Adapter.Adapter_baiviet;
import com.example.app_doc_truyen.Model.Comic;
//import com.example.demo_app_doc_truyen.GetDataInterface;
import com.example.demo_app_doc_truyen.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ListBaiViet_Fragment extends Fragment {
    RecyclerView recy;
    Adapter_baiviet adapter;
    ArrayList<Comic> list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_bai_viet_,container,false);
        recy = view.findViewById(R.id.recy);
        list = new ArrayList<>();
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            getActivity().setTheme(R.style.Theme_Dark);
        }else {
            getActivity().setTheme(R.style.Theme_Light);
        }
        recy.setHasFixedSize(true);
        recy.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new Adapter_baiviet( list,getContext());
        recy.setAdapter(adapter);
        getdata();
        return view;

    }

    private void getdata() {
        SharedPreferences preferences = getActivity().getSharedPreferences("user_file", Context.MODE_PRIVATE);
        String user = preferences.getString("gmail", "");
        String matkhau = preferences.getString("matkhau", "");
        StringRequest request = new StringRequest(Request.Method.POST, "https://myappnhom5.000webhostapp.com/appdoctruyen/listcomictacgia.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String result = jsonObject.getString("status");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    if(result.equals("thanh cong")){
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject object=jsonArray.getJSONObject(i);
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
                            Comic comic=list.get(0);
                            Log.e("tentacia",comic.getNameCM());
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

}