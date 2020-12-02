package com.hakino.turkishlanguage.util.admanager;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.hakino.turkishlanguage.R;

public class AdvancedNativeAdManager {
    private static final String TAG = AdvancedNativeAdManager.class.getSimpleName();
    public static AdvancedNativeAdManager mInstance;
    private IAdvencedNativeAdListener mAdvanedNativeAdListener;
    private static boolean isLoaded;

    public static AdvancedNativeAdManager getInstance(Context context, String nativeId) {
        if (mInstance == null) {
            mInstance = new AdvancedNativeAdManager(context, nativeId);
        }
        return mInstance;
    }
    private AdvancedNativeAdManager(Context context, String nativeId) {

    }

    private void populateUnifiedNativeAdView(UnifiedNativeAd nativeAd, UnifiedNativeAdView adView) {
        adView.setMediaView((MediaView) adView.findViewById(R.id.ad_media));
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        RelativeLayout layout = adView.findViewById(R.id.layout_container);
        layout.setBackgroundColor(Color.WHITE);
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        adView.setNativeAd(nativeAd);
        VideoController vc = nativeAd.getVideoController();
        if (vc.hasVideoContent()) {
            vc.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
                @Override
                public void onVideoEnd() {
                    super.onVideoEnd();
                }
            });
        } else {
        }
    }
    public void refreshNativeAd(final Context context, final FrameLayout frameLayout, final LinearLayout layoutAd, RelativeLayout bannerAd) {
        AdLoader.Builder builder = new AdLoader.Builder(context, nativeId);
        builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            @Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                if (nativeAd != null) {
                    nativeAd.destroy();
                }
                nativeAd = unifiedNativeAd;
                UnifiedNativeAdView adView = (UnifiedNativeAdView) LayoutInflater.from(context).inflate(R.layout.main_ad_unified, null);
                populateUnifiedNativeAdView(unifiedNativeAd, adView);
//                frameLayout.removeAllViews();
//                frameLayout.addView(adView);
            }

        });
        AdLoader adLoader = builder.withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int errorCode) {
                setupBannerAd(bannerAd);
            }
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                layoutAd.setVisibility(View.VISIBLE);
            }
        }).build();
        adLoader.loadAd(new AdRequest.Builder().build());
    }


    public interface IAdvencedNativeAdListener{
        void onAdvencedNativeAdLoaded();
        void onAdvencedNativeAdFailed();
    }
}
