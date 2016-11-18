package com.satti.memorygame.util;

public class Log {

	private static boolean enableLog = true;
	private static String TAG = "MemoryGameApp";
			
	
	public static void i(String msg) {
		if (enableLog)
			android.util.Log.i(TAG, msg);
	}

	public static void d(String msg) {
		if (enableLog)
			android.util.Log.d(TAG, msg);
	}

	public static void e(String msg) {
		if (enableLog)
			android.util.Log.e(TAG, msg);
	}

	public static void w(String msg) {
		if (enableLog)
			android.util.Log.w(TAG, msg);
	}

	public static void v(String msg) {
		if (enableLog)
			android.util.Log.v(TAG, msg);
	}
	
	
	public static void i(String tag, String msg) {
		if (enableLog)
			android.util.Log.i(tag, msg);
	}

	public static void d(String tag, String msg) {
		if (enableLog)
			android.util.Log.d(tag, msg);
	}

	public static void e(String tag, String msg) {
		if (enableLog)
			android.util.Log.e(tag, msg);
	}

	public static void w(String tag, String msg) {
		if (enableLog)
			android.util.Log.w(tag, msg);
	}

	public static void v(String tag, String msg) {
		if (enableLog)
			android.util.Log.v(tag, msg);
	}

}
