package com.example.app_doc_truyen.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app_doc_truyen.Model.Comic;
import com.example.demo_app_doc_truyen.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class BaiVietActivity extends AppCompatActivity {
    private ImageView imgAnhbaiviet , imngback;
    private TextView txtTDbaiviet,txtNDbaiviet,txtTenTg;
    ArrayList<Comic> ComicArrayList;
    int ID,IDTL;
    String NameCM,Content,ImgCM,Author,DateUp;
    String Favourite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bai_viet);
        imgAnhbaiviet = findViewById(R.id.imgAnhbaiviet);
        imngback = findViewById(R.id.imgback);
        txtTDbaiviet = findViewById(R.id.txtTDbaiviet);
        txtNDbaiviet=findViewById(R.id.txtNDbaiviet);
        txtTenTg=findViewById(R.id.txtTenTg);
        ComicArrayList=new ArrayList<>();
        //
        imngback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        GetInformation();
    }

    private void GetInformation() {
        Comic comic = (Comic) getIntent().getSerializableExtra("data");
        ID = comic.getIDcomic();
        IDTL = comic.getIDTL();
        NameCM = comic.getNameCM();
        Content = comic.getContent();
        ImgCM = comic.getImgCM();
        Author = comic.getAuthor();
        Favourite = comic.getFavourite();
        DateUp = comic.getDateUp();
        //
        txtTDbaiviet.setText(NameCM);
        txtNDbaiviet.setText(Content);
        txtTenTg.setText(Author);
        Picasso.get().load(ImgCM).into(imgAnhbaiviet);
    }
}