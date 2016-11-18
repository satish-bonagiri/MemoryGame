package com.satti.memorygame.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by satish on 18/11/16.
 */

public interface FlickrImageInterface {

    @GET("photos_public.gne?format=json")
    Call<ResponseBody> getFlickrModel();

}
