package com.satti.memorygame;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.satti.memorygame.adapter.AdapterModel;
import com.satti.memorygame.adapter.ImageAdapter;
import com.satti.memorygame.network.RetrofitNetworkClient;
import com.satti.memorygame.network.RetrofitOnDownloadListener;
import com.satti.memorygame.util.Log;
import com.satti.memorygame.util.Networkutil;
import com.satti.memorygame.util.ProgressUtil;
import com.satti.memorygame.util.TextUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * A placeholder fragment containing a simple view.
 */
public class StartUpActivityFragment extends Fragment implements RetrofitOnDownloadListener {

    private GridView mPhotoGridView;
    ImageAdapter mImageAdapter;
    //ArrayList<Drawable> mPhotosList;

    ImageView mShowImageView;
    ArrayList<AdapterModel> mPhotosList;
    TextView mTimerTextView;
    String mTimeFormat = "%02d:%02d";
    private MyCountDownTimer mTimer;
    private long interval = 500;

    private ArrayList<Integer> mRandomIntegerArrayList;

    private int currentPosition  = -1;

    public StartUpActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!Networkutil.isNetworkAvailable(getActivity())) {
            TextUtils.displayToast(getActivity(), getString(R.string.no_network));
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
        mPhotoGridView = (GridView) view.findViewById(R.id.photo_gridView);

        mShowImageView = (ImageView) view.findViewById(R.id.show_imageView);
        mTimerTextView = (TextView) view.findViewById(R.id.timer_textview);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        mShowImageView.setMinimumHeight(height / 5);
        mShowImageView.setMinimumWidth((width / 3));

        mPhotosList = new ArrayList<>();
        mRandomIntegerArrayList = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            mRandomIntegerArrayList.add(i);
        }
        Log.e("SATTI", "initial Array :: " + mRandomIntegerArrayList.toString());
        Collections.shuffle(mRandomIntegerArrayList);
        Log.e("SATTI", "Final Array :: " + mRandomIntegerArrayList.toString());

//        for(int i=0 ; i < 9 ;i++)
//             mPhotosList.add(getActivity().getResources().getDrawable(R.mipmap.ic_launcher));

        mImageAdapter = new ImageAdapter(getActivity(), mPhotosList, false);
        mPhotoGridView.setOnItemClickListener(onItemClickListener);
        mPhotoGridView.setAdapter(mImageAdapter);

        return view;
    }

    @Override
    public void onDownloadComplete(List<AdapterModel> flickritems) {
        ProgressUtil.hideProgressDialog();

        if (flickritems != null) {
            displayMsgDialog();
            for (int i = 0; i < 9; i++) {
                mPhotosList.add(flickritems.get(i));
            }
            mImageAdapter.notifyDataSetChanged();
        } else {
            //show here error dialog or network not available dialog !!!
        }
    }


    private void displayMsgDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(
                getActivity());
        builder.setTitle(getString(R.string.game_rules));
        String msgText = String.format(getString(R.string.prompt_text), 15);
        Spanned spannedText = Html.fromHtml(msgText);
        builder.setMessage(spannedText);
        builder.setCancelable(false);
        builder.setPositiveButton(getString(R.string.lets_start), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                mTimer = new MyCountDownTimer(15 * 1000, interval);
                mTimer.start();
            }
        });

        try {
            builder.show();
        } catch (WindowManager.BadTokenException e) {
        }
    }


    private class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            if (!isVisible()) { //if fragment is not visible,don't update the UI
                return;
            }
            mTimerTextView.setText(getResources().getString(R.string.lets_start));
            if (mTimer != null) {
                mTimer.cancel();
            }

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mShowImageView.setVisibility(View.VISIBLE);
                    mTimerTextView.setVisibility(View.GONE);

                    mImageAdapter.setGameStarted(true);
                    mImageAdapter.notifyDataSetChanged();
                    currentPosition = mRandomIntegerArrayList.get(0);
                    updateShowImage(currentPosition);
                }
            }, 2000);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            long seconds = millisUntilFinished / 1000 % 60;
            long minutes = (millisUntilFinished / (1000 * 60)) % 60;

            mTimerTextView.setText(String.format(Locale.getDefault(), mTimeFormat, minutes, seconds));
        }
    }

    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            if(mRandomIntegerArrayList.isEmpty() || !mImageAdapter.isGameStarted()){
               return;
            }
            AdapterModel adapterModel  = ((AdapterModel)parent.getAdapter().getItem(position));
            if(position == currentPosition){
                if (!adapterModel.isMatched()) {
                    mRandomIntegerArrayList.remove(mRandomIntegerArrayList.indexOf(currentPosition));
                    ((AdapterModel) parent.getAdapter().getItem(position)).setMatched(true);
                    mImageAdapter.notifyDataSetChanged();
                    Log.e("SATTI","After Remove ::: "+mRandomIntegerArrayList.toString());
                    if(!mRandomIntegerArrayList.isEmpty()){
                        currentPosition = mRandomIntegerArrayList.get(0);
                        updateShowImage(currentPosition);
                    }else{
                        TextUtils.displayToast(getActivity(),R.string.done);
                    }
                }else{
                    //Already matched ,no need to remove
                }
            }else{
                TextUtils.displayToast(getActivity(),R.string.try_again);
            }
        }
    };

    private void updateShowImage(int position) {
        Picasso.with(getActivity()).load(mPhotosList.get(position).getUrl())
                .error(R.mipmap.ic_launcher)
                .resize(120, 120)
                .centerCrop()
                .into(mShowImageView);
    }

}
