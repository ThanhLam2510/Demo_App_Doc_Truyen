package com.example.app_doc_truyen.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_doc_truyen.Activity.SeachcomicActivity;
import com.example.app_doc_truyen.Adapter.Adapter_baiviet;
import com.example.app_doc_truyen.Model.Comic;
import com.example.demo_app_doc_truyen.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Trangchu_Fragment extends Fragment {

    ViewFlipper viewFlipper;
    ArrayList<Comic> list;
    RecyclerView recyclerView_BaiViet;
    Adapter_baiviet adapter_baiviet;
    LinearLayout seachcomics;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trangchu_,container,false);
        viewFlipper = (ViewFlipper)view.findViewById(R.id.viewlipper);
        seachcomics=view.findViewById(R.id.seachcomics);
        seachcomics.setOnClickListener(v -> {
            Intent intent=new Intent(getActivity(), SeachcomicActivity.class);
            startActivity(intent);
        });
        recyclerView_BaiViet=view.findViewById(R.id.recyclerView_BaiViet);
        ViewFlip();
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            getActivity().setTheme(R.style.Theme_Dark);
        }else {
            getActivity().setTheme(R.style.Theme_Light);
        }
        list = new ArrayList<>();
        recyclerView_BaiViet.setHasFixedSize(true);
        recyclerView_BaiViet.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter_baiviet = new Adapter_baiviet(list, getActivity());
        recyclerView_BaiViet.setAdapter(adapter_baiviet);

        getdata();
        return view;
    }
    void ViewFlip()
    {
        ArrayList<String> mang = new ArrayList<>();
        mang.add("https://truyentranhtronbo.com/wp-content/uploads/2022/05/banner-truyen-tranh-tron-bo-bayvienngocrong.jpg");
        mang.add("https://truyenbanquyen.com/wp-content/uploads/2018/01/BANNER-WEB-180102.jpg");
        mang.add("https://congdongshop.com/wp-content/uploads/2022/05/banner-cong-dong-shop-truyen-tranh-full-bo.jpg");
        for(int i=0;i<mang.size();i++)
        {
            ImageView imageView = new ImageView(getActivity().getApplicationContext());
            Picasso.get().load(mang.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(4000);
        viewFlipper.setAutoStart(true);

        Animation animation_in = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),R.anim.slide_in);
        Animation animation_out = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),R.anim.slide_out);

        viewFlipper.setInAnimation(animation_in);
        viewFlipper.setOutAnimation(animation_out);
    }
    public void getdata(){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "https://myappnhom5.000webhostapp.com/appdoctruyen/getcomic.php", null,
                new com.android.volley.Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("aaaaaaa", String.valueOf(response));
                        for (int i = 0; i < response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
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
//                                Comic comic=list.get(0);
//                                Log.e("tentacia",comic.getNameCM());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter_baiviet.notifyDataSetChanged();
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
