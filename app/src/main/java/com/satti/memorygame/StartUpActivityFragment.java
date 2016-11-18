package com.satti.memorygame;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

/**
 * A placeholder fragment containing a simple view.
 */
public class StartUpActivityFragment extends Fragment {

    private GridView mPhotoGridView;

    public StartUpActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start_up, container, false);
        mPhotoGridView = (GridView)view.findViewById(R.id.photo_gridView);


        return  view;
    }
}
