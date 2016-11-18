package com.satti.memorygame.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.satti.memorygame.R;
import com.satti.memorygame.network.model.Item;
import com.satti.memorygame.util.Log;
import com.squareup.picasso.Picasso;


public class PhotoChooseAdapter extends BaseAdapter {

	ArrayList<Item> drawableArrayList;
	Context mContext;
	private int imageHeight;
	Display display;


	public PhotoChooseAdapter(Context context,ArrayList<Item> arrayList) {
		drawableArrayList = arrayList;
		mContext = context;
		display = ((Activity) mContext).getWindowManager().getDefaultDisplay();
		imageHeight = display.getHeight()/5;
	}

	public int getCount() {
		return drawableArrayList.size();
	}

	public Item getItem(int position) {
		return drawableArrayList.get(position);
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

		Picasso.with(mContext).load(drawableArrayList.get(position).getMedia().getM())
				.error(R.mipmap.ic_launcher)
				.resize(120,120)
				.centerCrop()
				.into(holder.imageView);
		return convertView;

	}

	class ViewHolder {
		ImageView imageView;
	}

}
