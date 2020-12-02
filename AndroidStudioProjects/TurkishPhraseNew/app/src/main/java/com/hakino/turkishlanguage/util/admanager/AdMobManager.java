package com.hakino.turkishlanguage.util;

import android.content.Context;
import android.os.Bundle;

import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class AdMobManager {
    private static final String TAG = AdMobManager.class.getSimpleName();
    private static AdMobManager mInstance;
    private InterstitialAd mInterstitialAd;
    private IInterstitialListener mInterstitialListener;
    public boolean adRequest = false; //Change this boolean inside 'onAdNotLoaded' if ad must be shown.

    public static AdMobManager getInstance(Context context, String interstitialId, String contentRating) {
        if (mInstance == null) {
            mInstance = new AdMobManager(context, interstitialId, contentRating);
        }
        return mInstance;
    }
    private AdMobManager(Context context, String interstitialId, final String contentRating) {
        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId(interstitialId);
        loadInterstitialAd(contentRating);
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                loadInterstitialAd(contentRating);
                if (mInterstitialListener != null)
                    mInterstitialListener.onAdClosed();
            }
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                if (adRequest) {
                    adRequest = false;
                    mInterstitialAd.show();
                }
            }
        });
    }
    private void loadInterstitialAd(String contentRating) {
        if (contentRating.isEmpty()) {
            mInterstitialAd.loadAd(new AdRequest.Builder().build());
        } else {
            Bundle extras = new Bundle();
            extras.putString(Constants.CONTENT_RATING_KEY, contentRating);
            mInterstitialAd.loadAd(new AdRequest.Builder()
                    .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                    .build());
        }
    }
    public void showInterstitialAd(IInterstitialListener iInterstitialListener) {
        this.mInterstitialListener = iInterstitialListener;
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
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
