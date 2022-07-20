package com.example.app_doc_truyen.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Adapter_quanlybaiviet extends RecyclerView.Adapter<Adapter_quanlybaiviet.Viewhollde_baiviet> {
    ArrayList<Comic> comicArrayList ;
    Context context ;
    TextInputEditText edittentruyenup,editlinkup,editmotaup;
    private Button btnHuyUpdate;
    private Button btnLuuUpdate;
    String tentruyenup,linkup,motaup,id;
    public Adapter_quanlybaiviet(ArrayList<Comic> comicArrayList, Context context) {
        this.comicArrayList = comicArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewhollde_baiviet onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context , R.layout.tab_qlbai_viet , null);

        return new Viewhollde_baiviet(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewhollde_baiviet holder, @SuppressLint("RecyclerView") int position) {
        Comic comic = comicArrayList.get(position) ;
        Picasso.get().load(comic.getImgCM())
                .into(holder.imgcomic);
        holder.noidungcomic.setText( comic.getContent());
        holder.tvTdcomic.setText(comic.getNameCM());
        holder.cardViewcomic.setOnClickListener(v -> {
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
        holder.xoaspcomic.setOnClickListener(v -> {
            AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
            builder.setTitle("Delete");
            builder.setMessage("Bạn có muốn xóa "+comic.getNameCM()+" không?");
            builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    StringRequest stringRequest=new StringRequest(Request.Method.POST, "https://myappnhom5.000webhostapp.com/appdoctruyen/Deletetruyen.php", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            JSONObject object= null;
                            try {
                                object = new JSONObject(response);
                                String check=object.getString("state");
                                if(check.equals("delete")){
                                    delete(position);
                                    Toast.makeText(v.getContext(), "Xóa Thành Công.", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(v.getContext(),"xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                        }
                    }){
                        @Override
                        protected Map<String,String> getParams() throws AuthFailureError{
                            HashMap<String,String> deHashMap=new HashMap<>();
                            deHashMap.put("IDcomic", String.valueOf(comic.getIDcomic()));
                            return deHashMap;
                        }
                    };
                    RequestQueue requestQue = Volley.newRequestQueue(v.getContext());
                    requestQue.add(stringRequest);
                }
            });
            AlertDialog dialog=builder.create();
            dialog.show();
        });
        holder.suaspcomic.setOnClickListener(v -> {
            View edit= LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_sua_sp,null);
            edittentruyenup = edit.findViewById(R.id.tentruyen_updatetruyen);
            editlinkup = edit.findViewById(R.id.img_updatetruyen);
            editmotaup = edit.findViewById(R.id.mota_updatetruyen);
            btnHuyUpdate = edit.findViewById(R.id.btnHuyUpdate);
            btnLuuUpdate = edit.findViewById(R.id.btnLuuUpdate);
            edittentruyenup.setText(comic.getNameCM());
            editlinkup.setText(comic.getImgCM());
            editmotaup.setText(comic.getContent());
            AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
            builder.setTitle("Sửa Thông tin Sản Phẩm "+comic.getNameCM());
            builder.setView(edit);
            AlertDialog dialo=builder.create();
            btnLuuUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences preferences = context.getSharedPreferences("user_file", Context.MODE_PRIVATE);
                   String user = preferences.getString("gmail", "");
                    String matkhau = preferences.getString("matkhau", "");
                    final ProgressDialog progressDialog = new ProgressDialog(v.getContext());
                    progressDialog.setMessage("Please Wait..");
                    tentruyenup=edittentruyenup.getText().toString();
                    linkup=editlinkup.getText().toString();
                    motaup=editmotaup.getText().toString();
                    id= String.valueOf(comic.getIDcomic());
                    DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                    String date = df.format(Calendar.getInstance().getTime());
                    if(!validateten()   |!validatehinhanh()|!validatemota() ){
                        return;
                    }
                    else{
                        progressDialog.show();
                        StringRequest request = new StringRequest(Request.Method.POST, "https://myappnhom5.000webhostapp.com/appdoctruyen/updatecomic.php", new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss();
                                Toast.makeText(v.getContext(), response, Toast.LENGTH_SHORT).show();
                                dialo.dismiss();
                            }
                        },new Response.ErrorListener(){

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.dismiss();
                                Toast.makeText(v.getContext(),"xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                            }
                        }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String,String> params = new HashMap<String, String>();
                                params.put("IDcomic", id);
                                params.put("Email", user);
                                params.put("Pass", matkhau);
                                params.put("NameCM", tentruyenup);
                                params.put("ImgCM", linkup);
                                params.put("Content", motaup);
                                params.put("Date", date);
                                return params;

                            }
                        };
                        RequestQueue requestQue = Volley.newRequestQueue(v.getContext());
                        requestQue.add(request);
                    }
                }
            });
            btnHuyUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialo.dismiss();
                }
            });
            dialo.show();
        });
    }

    @Override
    public int getItemCount() {
        return comicArrayList.size();
    }

    class Viewhollde_baiviet extends RecyclerView.ViewHolder {
        ImageView imgcomic,xoaspcomic,suaspcomic;
        TextView tvTdcomic,noidungcomic;
        ConstraintLayout cardViewcomic;
        public Viewhollde_baiviet(@NonNull View itemView) {
            super(itemView);
            imgcomic=itemView.findViewById(R.id.imgCommicql);
            xoaspcomic=itemView.findViewById(R.id.imgXoaSP);
            suaspcomic = itemView.findViewById(R.id.imgSuaSP);
            tvTdcomic = itemView.findViewById(R.id.itemTenTieuDeql) ;
            noidungcomic = itemView.findViewById(R.id.itemTenNoiDungql) ;
            cardViewcomic = itemView.findViewById(R.id.itemquanly) ;
        }
    }
    public void delete(int item){
        comicArrayList.remove(item);
        notifyItemRemoved(item);
    }
    public boolean validateten(){
        if(tentruyenup.isEmpty()){
            edittentruyenup.setError("Không được bỏ trống.");
            return false;
        } else{
            edittentruyenup.setError(null);
            return true;
        }
    }
    public boolean validatehinhanh(){
        if(linkup.isEmpty()){
            editlinkup.setError("Không được bỏ trống.");
            return false;
        } else{
            editlinkup.setError(null);
            return true;
        }
    }
    public boolean validatemota(){
        if(motaup.isEmpty()){
            editmotaup.setError("Không được bỏ trống.");
            return false;
        } else{
            editmotaup.setError(null);
            return true;
        }
    }
}


