package com.ats.rtoemployeeapp.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.rtoemployeeapp.R;
import com.ats.rtoemployeeapp.adapter.WorkStatusAdapter;
import com.ats.rtoemployeeapp.constants.Constants;
import com.ats.rtoemployeeapp.model.User;
import com.ats.rtoemployeeapp.model.WorkHeader;
import com.ats.rtoemployeeapp.util.CommonDialog;
import com.ats.rtoemployeeapp.util.CustomSharedPreference;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edFromDate, edToDate;
    private TextView tvFromDate, tvToDate;
    private LinearLayout llSearch;
    private RecyclerView recyclerView;

    ArrayList<WorkHeader> headerList = new ArrayList<>();

    WorkStatusAdapter adapter;

    long fromDateMillis, toDateMillis;

    int yyyy, mm, dd;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        setTitle(Html.fromHtml("<font color='#000000'>Report</font>"));

        edFromDate = findViewById(R.id.edFromDate);
        edToDate = findViewById(R.id.edToDate);
        llSearch = findViewById(R.id.llSearch);
        recyclerView = findViewById(R.id.recyclerView);
        tvFromDate = findViewById(R.id.tvFromDate);
        tvToDate = findViewById(R.id.tvToDate);

        llSearch.setOnClickListener(this);
        edFromDate.setOnClickListener(this);
        edToDate.setOnClickListener(this);

        String userStr = CustomSharedPreference.getString(this, CustomSharedPreference.KEY_USER);
        Gson gson = new Gson();
        user = gson.fromJson(userStr, User.class);
        Log.e("User Bean : ", "---------------" + user);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.edFromDate) {
            int yr, mn, dy;
            if (fromDateMillis > 0) {
                Calendar purchaseCal = Calendar.getInstance();
                purchaseCal.setTimeInMillis(fromDateMillis);
                yr = purchaseCal.get(Calendar.YEAR);
                mn = purchaseCal.get(Calendar.MONTH);
                dy = purchaseCal.get(Calendar.DAY_OF_MONTH);
            } else {
                Calendar purchaseCal = Calendar.getInstance();
                yr = purchaseCal.get(Calendar.YEAR);
                mn = purchaseCal.get(Calendar.MONTH);
                dy = purchaseCal.get(Calendar.DAY_OF_MONTH);
            }
            DatePickerDialog dialog = new DatePickerDialog(ReportActivity.this, fromDateListener, yr, mn, dy);
            dialog.show();

        } else if (view.getId() == R.id.edToDate) {
            int yr, mn, dy;
            if (toDateMillis > 0) {
                Calendar purchaseCal = Calendar.getInstance();
                purchaseCal.setTimeInMillis(toDateMillis);
                yr = purchaseCal.get(Calendar.YEAR);
                mn = purchaseCal.get(Calendar.MONTH);
                dy = purchaseCal.get(Calendar.DAY_OF_MONTH);
            } else {
                Calendar purchaseCal = Calendar.getInstance();
                yr = purchaseCal.get(Calendar.YEAR);
                mn = purchaseCal.get(Calendar.MONTH);
                dy = purchaseCal.get(Calendar.DAY_OF_MONTH);
            }
            DatePickerDialog dialog = new DatePickerDialog(ReportActivity.this, toDateListener, yr, mn, dy);
            dialog.show();

        } else if (view.getId() == R.id.llSearch) {

            if (edFromDate.getText().toString().isEmpty()) {
                edFromDate.setError("required");
            } else if (edToDate.getText().toString().isEmpty()) {
                edToDate.setError("required");
                edFromDate.setError(null);
            } else {
                edToDate.setError(null);
                edFromDate.setError(null);


                String fromDate = tvFromDate.getText().toString();
                String toDate = tvToDate.getText().toString();

                getWorkStatusListByDate(fromDate, toDate, user.getUserId());

            }

        }
    }


    DatePickerDialog.OnDateSetListener fromDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            yyyy = year;
            mm = month + 1;
            dd = dayOfMonth;
            edFromDate.setText(dd + "-" + mm + "-" + yyyy);
            tvFromDate.setText(yyyy + "-" + mm + "-" + dd);
            // fromDate = dd + "-" + mm + "-" + yyyy;

            Calendar calendar = Calendar.getInstance();
            calendar.set(yyyy, mm - 1, dd);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.HOUR, 0);
            fromDateMillis = calendar.getTimeInMillis();
        }
    };

    DatePickerDialog.OnDateSetListener toDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            yyyy = year;
            mm = month + 1;
            dd = dayOfMonth;
            edToDate.setText(dd + "-" + mm + "-" + yyyy);
            tvToDate.setText(yyyy + "-" + mm + "-" + dd);
            // toDate = dd + "-" + mm + "-" + yyyy;

            Calendar calendar = Calendar.getInstance();
            calendar.set(yyyy, mm - 1, dd);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.HOUR, 0);
            toDateMillis = calendar.getTimeInMillis();
        }
    };


    public void getWorkStatusListByDate(String fromDate, String toDate, int userId) {

        Log.e("PARAMETERS : ", "      FROM DATE : " + fromDate + "             TO DATE : " + toDate + "         USER ID : " + userId);

        if (Constants.isOnline(this)) {
            final CommonDialog commonDialog = new CommonDialog(this, "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<WorkHeader>> listCall = Constants.myInterface.getWorkHeaderListByDate(fromDate, toDate, userId);
            listCall.enqueue(new Callback<ArrayList<WorkHeader>>() {
                @Override
                public void onResponse(Call<ArrayList<WorkHeader>> call, Response<ArrayList<WorkHeader>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("WORK HEADER LIST : ", " - " + response.body());


                            headerList.clear();
                            headerList = response.body();

                            adapter = new WorkStatusAdapter(headerList, ReportActivity.this, 0);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ReportActivity.this);
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(adapter);

                            commonDialog.dismiss();

                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<WorkHeader>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(this, "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

}
