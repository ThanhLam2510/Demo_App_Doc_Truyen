package com.example.app_doc_truyen.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_doc_truyen.Adapter.Adapter_baiviet;
import com.example.app_doc_truyen.Adapter.Adapter_theloai;
import com.example.app_doc_truyen.Model.Comic;
import com.example.app_doc_truyen.Model.TLcomic;
import com.example.demo_app_doc_truyen.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ChuDe_Fragment extends Fragment {
    RecyclerView rcltheloai;
    ArrayList<TLcomic> postList;
    Adapter_theloai adaptertheloai;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chu_de_,container,false);
        rcltheloai=view.findViewById(R.id.rcltheloai);
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            getActivity().setTheme(R.style.Theme_Dark);
        }else {
            getActivity().setTheme(R.style.Theme_Light);
        }
        rcltheloai.setHasFixedSize(true);
        postList=new ArrayList<>();
        rcltheloai.setLayoutManager(new LinearLayoutManager(getContext()));
        adaptertheloai = new Adapter_theloai( postList,getContext());
        rcltheloai.setAdapter(adaptertheloai);
        getdata();
        return view;
    }
    public void getdata(){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "https://myappnhom5.000webhostapp.com/appdoctruyen/getTL.php", null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
//                        Log.e("aaaaaaa", String.valueOf(response));
                        for (int i = 0; i < response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                postList.add(new TLcomic(
                                        object.getInt("IDTL"),
                                        object.getString("NameTL")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adaptertheloai.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Lỗi !" , Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    @Override
    public void onViewCreated(@NonNull  View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}