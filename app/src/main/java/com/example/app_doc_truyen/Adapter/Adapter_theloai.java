package com.example.app_doc_truyen.Adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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
import com.example.app_doc_truyen.Activity.listTLActivity;
import com.example.app_doc_truyen.Model.Comic;
import com.example.app_doc_truyen.Model.TLcomic;
import com.example.demo_app_doc_truyen.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Adapter_theloai extends RecyclerView.Adapter<Adapter_theloai.Viewhollde_baiviet> {
    ArrayList<TLcomic> TLcomicArrayList ;
    Context context ;

    public Adapter_theloai(ArrayList<TLcomic> TLcomicArrayList, Context context) {
        this.TLcomicArrayList = TLcomicArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewhollde_baiviet onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context , R.layout.tab_theloai , null);

        return new Viewhollde_baiviet(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewhollde_baiviet holder, @SuppressLint("RecyclerView") int position) {
        TLcomic tlComic = TLcomicArrayList.get(position) ;
        holder.chude1.setText( tlComic.getNameTL());
        holder.carview.setOnClickListener(v -> {
            final ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Please Wait..");
            progressDialog.show();
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://myappnhom5.000webhostapp.com/appdoctruyen/phanchiaTL.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    Intent intent = new Intent(context, listTLActivity.class);
                    intent.putExtra("Key_2", tlComic.getIDTL());
                    context.startActivity(intent);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(context, "Xảy ra lỗi", Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("IDTL", String.valueOf(tlComic.getIDTL()));
                    return params;
                }
            };

            requestQueue.add(stringRequest);
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return TLcomicArrayList.size();
    }

    class Viewhollde_baiviet extends RecyclerView.ViewHolder {
        View carview;
        TextView chude1  ;
        public Viewhollde_baiviet(@NonNull View itemView) {
            super(itemView);
            carview=itemView.findViewById(R.id.carview1);
            chude1 = itemView.findViewById(R.id.chude1) ;
        }
    }
}


