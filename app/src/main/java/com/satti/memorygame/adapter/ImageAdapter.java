package com.satti.memorygame.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.satti.memorygame.R;
import com.squareup.picasso.Picasso;


public class ImageAdapter extends BaseAdapter {

	ArrayList<AdapterModel> imagesArrayList;
	Context mContext;
	private int imageHeight;
	Display display;
	boolean isGameStarted;

	public ImageAdapter(Context context, ArrayList<AdapterModel> arrayList,boolean isGameStarted) {
		imagesArrayList = arrayList;
		mContext = context;
		display = ((Activity) mContext).getWindowManager().getDefaultDisplay();
		imageHeight = display.getHeight()/5;
		this.isGameStarted = isGameStarted;
	}

	public int getCount() {
		return imagesArrayList.size();
	}

	public AdapterModel getItem(int position) {
		return imagesArrayList.get(position);
	}



	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.griditem_layout, null);

			holder = new ViewHolder();
			holder.imageView = (ImageView) convertView.findViewById(R.id.imageview);
			holder.imageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,imageHeight));
			holder.imageView.setScaleType(ScaleType.FIT_XY);
			holder.imageView.setPadding(2, 2, 2, 2);			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if(isGameStarted){
			Picasso.with(mContext).load(R.mipmap.ic_launcher)
					.resize(120,120)
					.centerCrop()
					.into(holder.imageView);
		}else{
			Picasso.with(mContext).load(imagesArrayList.get(position).getUrl())
					.error(R.mipmap.ic_launcher)
					.resize(120,120)
					.centerCrop()
					.into(holder.imageView);
		}
		return convertView;
	}

	class ViewHolder {
		ImageView imageView;
	}

	public boolean isGameStarted() {
		return isGameStarted;
	}

	public void setGameStarted(boolean gameStarted) {
		isGameStarted = gameStarted;
	}

}
