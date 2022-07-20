package com.example.app_doc_truyen.Fragment;

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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_doc_truyen.Adapter.Adapter_baiviet;
import com.example.app_doc_truyen.Model.Comic;
import com.example.demo_app_doc_truyen.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class YeuThich_Fragment extends Fragment {
    RecyclerView rclDStop;
    ArrayList<Comic> postList;
    Adapter_baiviet adapterbaiviet;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_yeu_thich_,container,false);
//        iddd=view.findViewById(R.id.iddd);
        rclDStop=view.findViewById(R.id.recy);
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            getActivity().setTheme(R.style.Theme_Dark);
        }else {
            getActivity().setTheme(R.style.Theme_Light);
        }
        rclDStop.setHasFixedSize(true);
        postList=new ArrayList<>();
        rclDStop.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterbaiviet = new Adapter_baiviet( postList,getContext());
        rclDStop.setAdapter(adapterbaiviet);
        getdata();
        return view;
    }
    public void getdata(){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "https://myappnhom5.000webhostapp.com/appdoctruyen/favorite.php", null,
                new com.android.volley.Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                postList.add(new Comic(
                                        object.getInt("IDcomic"),
                                        object.getInt("IDTL"),
                                        object.getString("NameCM"),
                                        object.getString("Content"),
                                        object.getString("ImgCM"),
                                        object.getString("Author"),
                                        object.getString("Favourite"),
                                        object.getString("Date")
                                ));
                                Comic comic=postList.get(0);
                                Log.e("tentacia",comic.getNameCM());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapterbaiviet.notifyDataSetChanged();
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Lỗi !" , Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}