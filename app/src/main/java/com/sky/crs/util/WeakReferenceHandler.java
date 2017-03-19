package com.sky.crs.util;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;

public abstract class WeakReferenceHandler<T> extends Handler {
	private WeakReference<T> weakRef;

	public WeakReferenceHandler(T t) {
		weakRef = new WeakReference<T>(t);
	}

	public WeakReferenceHandler(Looper looper, T t) {
		super(looper);
		weakRef = new WeakReference<>(t);
	}

	@Override
	public void handleMessage(Message msg) {
		T ref = weakRef.get();
		if (ref != null) {
			handleMessage(ref, msg);
		}
	}

	protected abstract void handleMessage(T ref, Message msg);
}
