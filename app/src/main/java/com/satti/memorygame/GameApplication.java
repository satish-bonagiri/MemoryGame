package com.satti.memorygame;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;

/**
 * Created by satish on 18/11/16.
 */

public class GameApplication extends Application {

    public ArrayList<Bitmap> mBitmaps;

    public Drawable mSelectedDrawable;

    @Override
    public void onCreate() {
        super.onCreate();
        mBitmaps = new ArrayList<Bitmap>();

    }
}
