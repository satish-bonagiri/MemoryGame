package com.satti.memorygame;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.satti.memorygame.adapter.PhotoChooseAdapter;
import com.satti.memorygame.network.RetrofitNetworkClient;
import com.satti.memorygame.network.RetrofitOnDownloadListener;
import com.satti.memorygame.network.model.Item;
import com.satti.memorygame.network.model.Media;
import com.satti.memorygame.util.Log;
import com.satti.memorygame.util.Networkutil;
import com.satti.memorygame.util.ProgressUtil;
import com.satti.memorygame.util.TextUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class StartUpActivityFragment extends Fragment implements RetrofitOnDownloadListener{

    private GridView mPhotoGridView;
    PhotoChooseAdapter mPhotoChooseAdapter;
    //ArrayList<Drawable> mPhotosList;

    ImageView mShowImageView;
    ArrayList<Item> mPhotosList;

    public StartUpActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!Networkutil.isNetworkAvailable(getActivity())){
            TextUtils.displayToast(getActivity(),getString(R.string.no_network));
            return;
            //Toast.makeText(StartUpActivity.this,getString(R.string.no_network),Toast.LENGTH_SHORT).show();
        }
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

        mShowImageView = (ImageView)view.findViewById(R.id.show_imageView);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        mShowImageView.setMinimumHeight(height/5);
        mShowImageView.setMinimumWidth((width/3));

        mPhotosList = new ArrayList<>();

//        for(int i=0 ; i < 9 ;i++)
//             mPhotosList.add(getActivity().getResources().getDrawable(R.mipmap.ic_launcher));

        mPhotoChooseAdapter = new PhotoChooseAdapter(getActivity(), mPhotosList);
        mPhotoGridView.setOnItemClickListener(onItemClickListener);
        mPhotoGridView.setAdapter(mPhotoChooseAdapter);

        return  view;
    }

    @Override
    public void onDownloadComplete(List<Item> flickritems) {
        ProgressUtil.hideProgressDialog();
        if(flickritems != null){
            for(int i=0 ; i < 9 ;i++){
                mPhotosList.add(flickritems.get(i));
            }
            mPhotoChooseAdapter.notifyDataSetChanged();
        }
    }

    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Item item = mPhotosList.get(position);
            Picasso.with(getActivity()).load(mPhotosList.get(position).getMedia().getM())
                    .error(R.mipmap.ic_launcher)
                    .resize(120,120)
                    .centerCrop()
                    .into(mShowImageView);
        }
    };
}
