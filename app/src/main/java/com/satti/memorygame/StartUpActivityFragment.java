package com.satti.memorygame;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.satti.memorygame.network.RetrofitNetworkClient;
import com.satti.memorygame.network.RetrofitOnDownloadListener;
import com.satti.memorygame.network.model.Item;
import com.satti.memorygame.util.Log;
import com.satti.memorygame.util.ProgressUtil;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class StartUpActivityFragment extends Fragment implements RetrofitOnDownloadListener{

    private GridView mPhotoGridView;

    public StartUpActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProgressUtil.displayProgressDialog(getActivity(), new ProgressUtil.DialogListener() {
            @Override
            public void onButtonPressed() {

            }
        });
        RetrofitNetworkClient.getFlickrItems(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start_up, container, false);
        mPhotoGridView = (GridView)view.findViewById(R.id.photo_gridView);


        return  view;
    }

    @Override
    public void onDownloadComplete(List<Item> flickritems) {
        ProgressUtil.hideProgressDialog();
        if(flickritems != null){
         //   Log.e("SATTI",flickritems.size() +" ");
         //   Log.e("SATTI",flickritems.toString());
        }
    }
}
