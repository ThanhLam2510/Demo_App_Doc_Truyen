package com.example.app_doc_truyen.Fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.app_doc_truyen.Model.Comic;
import com.example.app_doc_truyen.Model.User;
import com.example.demo_app_doc_truyen.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;


public class ThemBaiViet_Fragment extends Fragment {
    FloatingActionButton btnfloat  ;
    TextInputEditText name , author , category , description ;
    Button btn_addtruyen  ;
    ImageView avatar ;
    List<User> userList  = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_them_bai_viet_,container,false);
        //
        btnfloat =view.findViewById(R.id.fab_themnhanvien) ;

        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            getActivity().setTheme(R.style.Theme_Dark);
        }else {
            getActivity().setTheme(R.style.Theme_Light);
        }
        //
        btnfloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_addtruyen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        if (Name.equalsIgnoreCase("") || Author.equalsIgnoreCase("")
//                                || Category.equalsIgnoreCase("") || Description.equalsIgnoreCase("")
//                                || ngayup.equalsIgnoreCase("")){
//                            Toast.makeText(builder.getContext(), "Hãy nhập hết tất cả các trường trên form!", Toast.LENGTH_SHORT).show();
//                        }
                    }
                });

            }
        });



        return view;

    }

}