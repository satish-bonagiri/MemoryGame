package com.satti.memorygame.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class TextUtils {

	public static void displayToast(Context context,String string){
		Toast  toast = Toast.makeText(context, string, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.show();
	}
	
	public static void displayToast(Context context, int resId){
		Toast  toast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.show();
	}
}
