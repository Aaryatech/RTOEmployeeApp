package com.ats.rtoemployeeapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.rtoemployeeapp.R;
import com.ats.rtoemployeeapp.activity.DetailActivity;
import com.ats.rtoemployeeapp.activity.HomeActivity;
import com.ats.rtoemployeeapp.constants.Constants;
import com.ats.rtoemployeeapp.model.Info;
import com.ats.rtoemployeeapp.model.WorkHeader;
import com.ats.rtoemployeeapp.util.CommonDialog;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkStatusAdapter extends RecyclerView.Adapter<WorkStatusAdapter.MyViewHolder> {

    private ArrayList<WorkHeader> workList;
    private Context context;
    private int type;

  /*  public WorkStatusAdapter(ArrayList<WorkHeader> workList, Context context) {
        this.workList = workList;
        this.context = context;
    }*/

    public WorkStatusAdapter(ArrayList<WorkHeader> workList, Context context, int type) {
        this.workList = workList;
        this.context = context;
        this.type = type;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvWorkType, tvDate, tvCustName, tvCustMobile, tvCustEmail, tvCustAddress;
        public Button btnCollect;
        public LinearLayout linearLayout;

        public MyViewHolder(View view) {
            super(view);
            tvWorkType = view.findViewById(R.id.tvWorkType);
            tvDate = view.findViewById(R.id.tvDate);
            tvCustName = view.findViewById(R.id.tvCustName);
            tvCustMobile = view.findViewById(R.id.tvCustMobile);
            tvCustEmail = view.findViewById(R.id.tvCustEmail);
            tvCustAddress = view.findViewById(R.id.tvCustAddress);

            btnCollect = view.findViewById(R.id.btnCollect);

            linearLayout = view.findViewById(R.id.linearLayout);

        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_work_status, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final WorkHeader model = workList.get(position);

        if (type == 0) {
            holder.btnCollect.setVisibility(View.GONE);
        } else {
            holder.btnCollect.setVisibility(View.VISIBLE);
        }

        holder.tvWorkType.setText("" + model.getWorkTypeName());
        holder.tvDate.setText("" + model.getDate1());
        holder.tvCustName.setText("" + model.getCustName());
        holder.tvCustAddress.setText("" + model.getAddPincode());
        holder.tvCustEmail.setText("" + model.getCustEmail());
        holder.tvCustMobile.setText("" + model.getCustMobile());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Gson gson = new Gson();
                String str = gson.toJson(model);

                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("Data", str);
                intent.putExtra("Type", type);
                context.startActivity(intent);

            }
        });

        holder.btnCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Integer> workIdList = new ArrayList<>();
                workIdList.add(model.getWorkId());

                updateWorkStatus(workIdList, 5);
            }
        });


    }

    @Override
    public int getItemCount() {
        return workList.size();
    }

    public void updateWorkStatus(ArrayList<Integer> workIdList, int status) {
        if (Constants.isOnline(context)) {
            final CommonDialog commonDialog = new CommonDialog(context, "Loading", "Please Wait...");
            commonDialog.show();

            Call<Info> listCall = Constants.myInterface.updateWorkStatus(workIdList, status);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("UPDATE STATUS : ", "------------" + response.body());

                            Info data = response.body();
                            if (data.getError()) {
                                Toast.makeText(context, "Unable to process!", Toast.LENGTH_SHORT).show();
                            } else {
                                HomeActivity activity = (HomeActivity) context;
                                activity.refreshData();
                            }

                            commonDialog.dismiss();

                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");
                            Toast.makeText(context, "Unable to process!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        Toast.makeText(context, "Unable to process!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<Info> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    Toast.makeText(context, "Unable to process!", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(context, "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

}
