package com.ats.rtoemployeeapp.interfaces;

import com.ats.rtoemployeeapp.model.Info;
import com.ats.rtoemployeeapp.model.LoginResponse;
import com.ats.rtoemployeeapp.model.WorkHeader;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface InterfaceApi {

    @POST("loginResponseUser")
    Call<LoginResponse> doLogin(@Query("userMobile") String userMobile, @Query("userPassword") String userPassword);

    @POST("updateUsrToken")
    Call<Info> updateToken(@Query("userId") int userId, @Query("token") String token);

    @POST("getCustWorkByUserId")
    Call<ArrayList<WorkHeader>> getWorkHeaderList(@Query("status") int status, @Query("userId") int userId);

    @POST("updateWorkStatus")
    Call<Info> updateWorkStatus(@Query("workIdList") ArrayList<Integer> workIdList, @Query("status") int status);

    @POST("getCustWorkByUserIdAndDate")
    Call<ArrayList<WorkHeader>> getWorkHeaderListByDate(@Query("fromDate") String fromDate, @Query("toDate") String toDate, @Query("userId") int userId);


}
