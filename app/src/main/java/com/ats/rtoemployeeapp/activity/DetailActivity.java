package com.ats.rtoemployeeapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.rtoemployeeapp.R;
import com.ats.rtoemployeeapp.constants.Constants;
import com.ats.rtoemployeeapp.model.Info;
import com.ats.rtoemployeeapp.model.WorkHeader;
import com.ats.rtoemployeeapp.util.CommonDialog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private TextView tvCustName, tvCustMobile, tvCustEmail, tvCustAddress, tvWorkType, tvDate, tvActualCost, tvAmtPaid, tvAmtRemaining, tvStatus, tvPhoto1, tvPhoto2, tvAadhar, tvRC, tvInsc1, tvInsc2, tvPUC, tvBankLetter, tvForm17, tvBankNOC, tvAddProof, tvOrigLic;
    private Button btnCollect;

    WorkHeader model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


        setTitle(Html.fromHtml("<font color='#000000'>Work Detail</font>"));

        tvCustName = findViewById(R.id.tvCustName);
        tvCustMobile = findViewById(R.id.tvCustMobile);
        tvCustEmail = findViewById(R.id.tvCustEmail);
        tvCustAddress = findViewById(R.id.tvCustAddress);
        tvWorkType = findViewById(R.id.tvWorkType);
        tvDate = findViewById(R.id.tvDate);
        tvActualCost = findViewById(R.id.tvActualCost);
        tvAmtPaid = findViewById(R.id.tvAmtPaid);
        tvAmtRemaining = findViewById(R.id.tvAmtRemaining);
        tvStatus = findViewById(R.id.tvStatus);

        btnCollect = findViewById(R.id.btnCollect);

        tvPhoto1 = findViewById(R.id.tvPhoto1);
        tvPhoto2 = findViewById(R.id.tvPhoto2);
        tvAadhar = findViewById(R.id.tvAadhar);
        tvRC = findViewById(R.id.tvRC);
        tvInsc1 = findViewById(R.id.tvInsc1);
        tvInsc2 = findViewById(R.id.tvInsc2);
        tvPUC = findViewById(R.id.tvPUC);
        tvBankLetter = findViewById(R.id.tvBankLetter);
        tvForm17 = findViewById(R.id.tvForm17);
        tvBankNOC = findViewById(R.id.tvBankNOC);
        tvAddProof = findViewById(R.id.tvAddProof);
        tvOrigLic = findViewById(R.id.tvOrigLic);


        String str = "";
        int type = 0;
        try {
            str = getIntent().getStringExtra("Data");
            type = getIntent().getIntExtra("Type", 0);
        } catch (Exception e) {
        }

        if (type == 0) {
            btnCollect.setVisibility(View.GONE);
        } else {
            btnCollect.setVisibility(View.VISIBLE);
        }

        Gson gson1 = new Gson();
        final WorkHeader model = gson1.fromJson(str, WorkHeader.class);

        if (model != null) {

            tvCustName.setText("" + model.getCustName());
            tvCustMobile.setText("" + model.getCustMobile());
            tvCustEmail.setText("" + model.getCustEmail());
            tvCustAddress.setText("" + model.getAddPincode());
            tvWorkType.setText("" + model.getWorkTypeName());
            tvDate.setText("" + model.getDate1());
            tvActualCost.setText("" + model.getWorkCost());
            tvAmtPaid.setText("" + model.getExInt1());
            tvAmtRemaining.setText("" + model.getExInt2());

            String status = "";
            if (model.getStatus() == 1) {
                status = "Upload Documents";
            } else if (model.getStatus() == 2) {
                status = "Update Work Cost";
            } else if (model.getStatus() == 3) {
                status = "Update Payment Done";
            } else if (model.getStatus() == 4) {
                status = "User Allocation";
            } else if (model.getStatus() == 5) {
                status = "Document In Office";
            } else if (model.getStatus() == 6) {
                status = "Document Submit to RTO";
            } else if (model.getStatus() == 7) {
                status = "Handover To Customer";
            }
            tvStatus.setText(status);

            if (model.getPhoto1() != null) {
                if (!model.getPhoto1().isEmpty()) {
                    tvPhoto2.setVisibility(View.VISIBLE);
                } else {
                    tvPhoto2.setVisibility(View.GONE);
                }
            }

            if (model.getPhoto() != null) {
                if (!model.getPhoto().isEmpty()) {
                    tvPhoto1.setVisibility(View.VISIBLE);
                } else {
                    tvPhoto1.setVisibility(View.GONE);
                }
            }

            if (model.getAdharCard() != null) {
                if (!model.getAdharCard().isEmpty()) {
                    tvAadhar.setVisibility(View.VISIBLE);
                } else {
                    tvAadhar.setVisibility(View.GONE);
                }
            }

            if (model.getRcbook() != null) {
                if (!model.getRcbook().isEmpty()) {
                    tvRC.setVisibility(View.VISIBLE);
                } else {
                    tvRC.setVisibility(View.GONE);
                }
            }

            if (model.getInsurance() != null) {
                if (!model.getInsurance().isEmpty()) {
                    tvInsc1.setVisibility(View.VISIBLE);
                } else {
                    tvInsc1.setVisibility(View.GONE);
                }
            }

            if (model.getInsurance1() != null) {
                if (!model.getInsurance1().isEmpty()) {
                    tvInsc2.setVisibility(View.VISIBLE);
                } else {
                    tvInsc2.setVisibility(View.GONE);
                }
            }

            if (model.getPuc() != null) {
                if (!model.getPuc().isEmpty()) {
                    tvPUC.setVisibility(View.VISIBLE);
                } else {
                    tvPUC.setVisibility(View.GONE);
                }
            }

            if (model.getAddProof() != null) {
                if (!model.getAddProof().isEmpty()) {
                    tvAddProof.setVisibility(View.VISIBLE);
                } else {
                    tvAddProof.setVisibility(View.GONE);
                }
            }

            if (model.getOrignalLicence() != null) {
                if (!model.getOrignalLicence().isEmpty()) {
                    tvOrigLic.setVisibility(View.VISIBLE);
                } else {
                    tvOrigLic.setVisibility(View.GONE);
                }
            }

            if (model.getBankDocument() != null) {
                if (!model.getBankDocument().isEmpty()) {
                    tvBankLetter.setVisibility(View.VISIBLE);
                } else {
                    tvBankLetter.setVisibility(View.GONE);
                }
            }

            if (model.getBankDocument1() != null) {
                if (!model.getBankDocument1().isEmpty()) {
                    tvBankNOC.setVisibility(View.VISIBLE);
                } else {
                    tvBankNOC.setVisibility(View.GONE);
                }
            }

            if (model.getExStr2() != null) {
                if (!model.getExStr2().isEmpty()) {
                    tvForm17.setVisibility(View.VISIBLE);
                } else {
                    tvForm17.setVisibility(View.GONE);
                }
            }

            tvPhoto1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(DetailActivity.this, ImageZoomActivity.class);
                    intent.putExtra("image", "" + model.getPhoto());
                    startActivity(intent);
                }
            });

            tvPhoto2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(DetailActivity.this, ImageZoomActivity.class);
                    intent.putExtra("image", "" + model.getPhoto1());
                    startActivity(intent);
                }
            });

            tvAadhar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(DetailActivity.this, ImageZoomActivity.class);
                    intent.putExtra("image", "" + model.getAdharCard());
                    startActivity(intent);
                }
            });

            tvRC.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(DetailActivity.this, ImageZoomActivity.class);
                    intent.putExtra("image", "" + model.getRcbook());
                    startActivity(intent);
                }
            });

            tvInsc1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(DetailActivity.this, ImageZoomActivity.class);
                    intent.putExtra("image", "" + model.getInsurance());
                    startActivity(intent);
                }
            });

            tvInsc2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(DetailActivity.this, ImageZoomActivity.class);
                    intent.putExtra("image", "" + model.getInsurance1());
                    startActivity(intent);
                }
            });

            tvPUC.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(DetailActivity.this, ImageZoomActivity.class);
                    intent.putExtra("image", "" + model.getPuc());
                    startActivity(intent);
                }
            });

            tvBankLetter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(DetailActivity.this, ImageZoomActivity.class);
                    intent.putExtra("image", "" + model.getBankDocument());
                    startActivity(intent);
                }
            });

            tvForm17.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(DetailActivity.this, ImageZoomActivity.class);
                    intent.putExtra("image", "" + model.getExStr2());
                    startActivity(intent);
                }
            });


            tvBankNOC.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(DetailActivity.this, ImageZoomActivity.class);
                    intent.putExtra("image", "" + model.getBankDocument1());
                    startActivity(intent);
                }
            });

            tvAddProof.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(DetailActivity.this, ImageZoomActivity.class);
                    intent.putExtra("image", "" + model.getAddProof());
                    startActivity(intent);
                }
            });

            tvOrigLic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(DetailActivity.this, ImageZoomActivity.class);
                    intent.putExtra("image", "" + model.getOrignalLicence());
                    startActivity(intent);
                }
            });


            btnCollect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ArrayList<Integer> workIdList = new ArrayList<>();
                    workIdList.add(model.getWorkId());

                    updateWorkStatus(workIdList, 5);
                }
            });
        }

    }

    public void updateWorkStatus(ArrayList<Integer> workIdList, int status) {
        Log.e("PARAMETER : ","    WORK ID LIST : "+workIdList+"                      STATUS : "+status);

        if (Constants.isOnline(this)) {
            final CommonDialog commonDialog = new CommonDialog(this, "Loading", "Please Wait...");
            commonDialog.show();

            Call<Info> listCall = Constants.myInterface.updateWorkStatus(workIdList, status);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("UPDATE STATUS : ", "------------" + response.body());

                            Info data = response.body();
                            if (!data.getError()) {
                                Intent intent = new Intent(DetailActivity.this, HomeActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);


                            } else {
                                Toast.makeText(DetailActivity.this, "Unable to process!", Toast.LENGTH_SHORT).show();
                            }

                            commonDialog.dismiss();

                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");
                            Toast.makeText(DetailActivity.this, "Unable to process!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        Toast.makeText(DetailActivity.this, "Unable to process!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<Info> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    Toast.makeText(DetailActivity.this, "Unable to process!", Toast.LENGTH_SHORT).show();
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
