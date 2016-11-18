package com.satti.memorygame.network;

import com.google.gson.Gson;
import com.satti.memorygame.network.model.FlickrModel;
import com.satti.memorygame.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by satish on 18/11/16.
 */

public class RetrofitNetworkClient {


    public static final String BASE_URL = "https://api.flickr.com/services/feeds/";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static void getFlickrItems(final RetrofitOnDownloadListener retrofitOnDownloadListener){

        final FlickrImageInterface flickrImageInterface  = RetrofitNetworkClient.getClient().create(FlickrImageInterface.class);

        Call<ResponseBody> responseBodyCall = flickrImageInterface.getFlickrModel();

        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String jsonStr = null;
                try {
                    jsonStr = response.body().string();
                    if(!jsonStr.isEmpty()){
                        Gson gson = new Gson();
                        jsonStr = jsonStr.replace("jsonFlickrFeed(","");
                        JSONObject jsonObject = new JSONObject(jsonStr);
                        FlickrModel flickrModel = gson.fromJson(jsonObject.toString(),FlickrModel.class);
                        if(flickrModel != null){
                             if(flickrModel.getItems() != null){

                             }
                            retrofitOnDownloadListener.onDownloadComplete(flickrModel.getItems());
                        }else{
                            retrofitOnDownloadListener.onDownloadComplete(null);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                retrofitOnDownloadListener.onDownloadComplete(null);
            }
        });

    }


}
