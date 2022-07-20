package com.example.app_doc_truyen.Adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_doc_truyen.Activity.BaiVietActivity;
import com.example.app_doc_truyen.Model.Comic;
import com.example.demo_app_doc_truyen.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Adapter_seachcomic extends RecyclerView.Adapter<Adapter_seachcomic.Viewhollde_baiviet> {
    ArrayList<Comic> comicArrayList ;
    Context context ;
    String tym;
    public Adapter_seachcomic(ArrayList<Comic> comicArrayList, Context context) {
        this.comicArrayList = comicArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewhollde_baiviet onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_seachcm , parent, false);
        return new Viewhollde_baiviet(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewhollde_baiviet holder, @SuppressLint("RecyclerView") int position) {
        Comic comic = comicArrayList.get(position) ;
        Picasso.get().load(comic.getImgCM())
                .into(holder.avatar);
        holder.noidung.setText( comic.getContent());
        holder.tieude.setText(comic.getNameCM());
        holder.date.setText("Up: "+comic.getDateUp());
        Log.e("aaaaaaaaaaaaaaaa", String.valueOf(comic.getFavourite()));
        if(comic.getFavourite().equals("True")){
            holder.anhn2.setImageResource(R.drawable.ic_baseline_favorite_24);
//            holder.anhn2.setVisibility(View.INVISIBLE);
            tym="False";
        }else if(comic.getFavourite().equals("False")){
            holder.anhn2.setImageResource(R.drawable.ic_baseline_favorite_border_24);
            tym="True";
        }
        holder.anhn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(v.getContext());
                progressDialog.setMessage("Please Wait..");
                progressDialog.show();
                StringRequest request = new StringRequest(Request.Method.POST, "https://myappnhom5.000webhostapp.com/appdoctruyen/tym.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                            holder.anhn2.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText( context, "xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Favourite", tym);
                        Log.e("ashsshs",tym);
                        params.put("IDcomic", String.valueOf(comic.getIDcomic()));
                        return params;

                    }
                };
                RequestQueue requestQue = Volley.newRequestQueue( context);
                requestQue.add(request);
            }
        });
        holder.carview.setOnClickListener(v -> {
            final ProgressDialog progressDialog = new ProgressDialog(v.getContext());
            progressDialog.setMessage("Please Wait..");
            progressDialog.show();
            RequestQueue requestQueue = Volley.newRequestQueue(v.getContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://myappnhom5.000webhostapp.com/appdoctruyen/laytruyen.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    Intent intent = new Intent(context, BaiVietActivity.class);
                    intent.putExtra("data",comicArrayList.get(position));
                    context.startActivity(intent);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(v.getContext(), "Xảy ra lỗi", Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
//                    params.put("Name",comic.getName());
                    return params;
                }
            };

            requestQueue.add(stringRequest);
            notifyItemChanged(position);
        });
    }



    @Override
    public int getItemCount() {
        return comicArrayList.size();
    }

    class Viewhollde_baiviet extends RecyclerView.ViewHolder {
        ImageView avatar,anhn2;
        ConstraintLayout carview;
        TextView tieude , noidung,date ;
        public Viewhollde_baiviet(@NonNull View itemView) {
            super(itemView);
            anhn2=itemView.findViewById(R.id.anhtym);
            carview=itemView.findViewById(R.id.carview);
            avatar = itemView.findViewById(R.id.imgCommic);
            tieude = itemView.findViewById(R.id.edtTieuDe) ;
            noidung = itemView.findViewById(R.id.edtNoiDung) ;
            date=itemView.findViewById(R.id.itemdate);
        }
    }
}


