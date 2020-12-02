package com.hakino.turkishlanguage.util;

import android.content.Context;
import android.util.Log;

import com.appbrain.AdId;
import com.appbrain.InterstitialBuilder;
import com.appbrain.InterstitialListener;

public class AppBrainManager {
    private static String TAG = AppBrainManager.class.getSimpleName();
    private static AppBrainManager mInstance;
    private InterstitialBuilder interstitialBuilder;
    private IInterstitialListener mInterstitialListener;
    private static boolean isLoaded;

    public static AppBrainManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new AppBrainManager(context);
        }
        return mInstance;
    }
    private AppBrainManager(final Context context) {
        interstitialBuilder = InterstitialBuilder.create()
                .setAdId(AdId.LEVEL_COMPLETE)
                .setListener(new InterstitialListener() {
                    @Override
                    public void onPresented() {
                        Log.e(TAG, "onPresented!");
                    }
                    @Override
                    public void onClick() {
                        Log.e(TAG, "onClick!");
                    }
                    @Override
                    public void onDismissed(boolean b) {
                        if (mInterstitialListener != null)
                            mInterstitialListener.onAdClosed();
                    }
                    @Override
                    public void onAdLoaded() {
                        isLoaded = true;
                    }
                    @Override
                    public void onAdFailedToLoad(InterstitialError interstitialError) {
                        if (mInterstitialListener != null)
                            mInterstitialListener.onAdNotLoaded();
                        isLoaded = false;
                    }
                })
                .setOnDoneCallback(new Runnable() {
                    @Override
                    public void run() {
                        // Preload again, so we can use interstitialBuilder again.
                        interstitialBuilder.preload(context);
                    }
                }).preload(context);
    }

    public void showAppBrainInterstitialAd(IInterstitialListener iInterstitialListener, Context context) {
        this.mInterstitialListener = iInterstitialListener;
        if (isLoaded) {
            interstitialBuilder.show(context);
        } else {
            if (mInterstitialListener != null)
                mInterstitialListener.onAdNotLoaded();
        }
    }

    public interface IInterstitialListener {
        void onAdClosed(); //If anything extra needs to do in 'OnAdCloseListener'.
        void onAdNotLoaded(); //What to do when ad has not loaded yet.
    }
}
