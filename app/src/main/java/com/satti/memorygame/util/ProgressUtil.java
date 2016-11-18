package com.satti.memorygame.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.KeyEvent;
import android.view.WindowManager.BadTokenException;

public class ProgressUtil {
    
	public interface DialogListener {
		void onButtonPressed();
	}
	
	private static ProgressDialog sDialog;
	
	public static void displayProgressDialog(Context context, final DialogListener listener) {
		sDialog = new ProgressDialog(context){
		
			public boolean onKeyDown(int keyCode, KeyEvent event) {
				if(event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
					hideProgressDialog();
					listener.onButtonPressed();
					return true;
					
				}
				else{
					return super.onKeyDown(keyCode, event);
				}
			}
		};
		sDialog.setMessage(" Please wait... ");
		try {
			sDialog.show();
		} catch (BadTokenException e) {
			e.printStackTrace();
		}
	}
	
	public static void hideProgressDialog() {
		if(sDialog != null && sDialog.isShowing()) {
			sDialog.dismiss();
			sDialog = null;
		}
	}
}