package com.imanloo.photocollage.remoteplus.model;

import com.google.gson.annotations.SerializedName;

public class Ad {
    @SerializedName("Status")
    private int status;

    @SerializedName("id")
    private String id;

    @SerializedName("pn")
    private String packagename;

    @SerializedName("au")
    private AutoUpdate autoUpdate;

    @SerializedName("dli")
    private Dialog dialog;

    @SerializedName("abnt")
    private String banner_id;

    @SerializedName("arbnt")
    private String rate_id;

    @SerializedName("aapt")
    private String app_id;

    @SerializedName("ant")
    private String native_id;

    @SerializedName("art")
    private String reward_id;

    @SerializedName("ain")
    private String inter_id;

    @SerializedName("ppg")
    private String privacy_google;

    @SerializedName("ppc")
    private String privacy_cafe;

    @SerializedName("ppm")
    private String privacy_myket;

    @SerializedName("tapt")
    private String tapsell_app_id;

    @SerializedName("tbnt")
    private String tapsell_banner_id;

    @SerializedName("trbnt")
    private String tapsell_rate_id;

    @SerializedName("tnt")
    private String tapsell_native_id;

    @SerializedName("tin")
    private String tapsell_inter_id;

    @SerializedName("trt")
    private String tapsell_reward_id;

    @SerializedName("cr")
    private String content_rating;

    @SerializedName("gam")
    private boolean isGoogle_admob;

    @SerializedName("gab")
    private boolean isGoogle_apbrain;

    @SerializedName("cam")
    private boolean isCafe_admob;

    @SerializedName("cab")
    private boolean isCafe_apbrain;

    @SerializedName("ctc")
    private boolean isCafe_tapsell;

    @SerializedName("cpa")
    private boolean isCafe_pandora;

    public String getReward_id() {
        return reward_id;
    }
    public void setReward_id(String reward_id) {
        this.reward_id = reward_id;
    }

    public String getPrivacy_google() {
        return privacy_google;
    }
    public void setPrivacy_google(String privacy_google) {
        this.privacy_google = privacy_google;
    }

    public String getPrivacy_cafe() {
        return privacy_cafe;
    }
    public void setPrivacy_cafe(String privacy_cafe) {
        this.privacy_cafe = privacy_cafe;
    }

    public String getPrivacy_myket() {
        return privacy_myket;
    }
    public void setPrivacy_myket(String privacy_myket) {
        this.privacy_myket = privacy_myket;
    }

    public String getTapsell_app_id() {
        return tapsell_app_id;
    }
    public void setTapsell_app_id(String tapsell_app_id) {
        this.tapsell_app_id = tapsell_app_id;
    }

    public String getTapsell_banner_id() {
        return tapsell_banner_id;
    }
    public void setTapsell_banner_id(String tapsell_banner_id) {
        this.tapsell_banner_id = tapsell_banner_id;
    }

    public String getTapsell_rate_id() {
        return tapsell_rate_id;
    }
    public void setTapsell_rate_id(String tapsell_rate_id) {
        this.tapsell_rate_id = tapsell_rate_id;
    }

    public AutoUpdate getAutoUpdate() {
        return autoUpdate;
    }
    public void setAutoUpdate(AutoUpdate autoUpdate) {
        this.autoUpdate = autoUpdate;
    }

    public Dialog getDialog() {
        return dialog;
    }
    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    public String getTapsell_native_id() {
        return tapsell_native_id;
    }
    public void setTapsell_native_id(String tapsell_native_id) {
        this.tapsell_native_id = tapsell_native_id;
    }

    public String getTapsell_inter_id() {
        return tapsell_inter_id;
    }
    public void setTapsell_inter_id(String tapsell_inter_id) {
        this.tapsell_inter_id = tapsell_inter_id;
    }

    public String getTapsell_reward_id() {
        return tapsell_reward_id;
    }
    public void setTapsell_reward_id(String tapsell_reward_id) {
        this.tapsell_reward_id = tapsell_reward_id;
    }

    public String getBanner_id() {
        return banner_id;
    }
    public void setBanner_id(String banner_id) {
        this.banner_id = banner_id;
    }

    public String getRate_id() {
        return rate_id;
    }
    public void setRate_id(String rate_id) {
        this.rate_id = rate_id;
    }

    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }

    public String getApp_id() {
        return app_id;
    }
    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getNative_id() {
        return native_id;
    }
    public void setNative_id(String native_id) {
        this.native_id = native_id;
    }

    public String getPackagename() {
        return packagename;
    }
    public void setPackagename(String packagename) {
        this.packagename = packagename;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getInter_id() {
        return inter_id;
    }
    public void setInter_id(String inter_id) {
        this.inter_id = inter_id;
    }

    public String getContent_rating() {
        return content_rating;
    }
    public void setContent_rating(String content_rating) {
        this.content_rating = content_rating;
    }

    public boolean isGoogle_admob() {
        return isGoogle_admob;
    }
    public void setGoogle_admob(boolean google_admob) {
        isGoogle_admob = google_admob;
    }

    public boolean isGoogle_apbrain() {
        return isGoogle_apbrain;
    }
    public void setGoogle_apbrain(boolean google_apbrain) {
        isGoogle_apbrain = google_apbrain;
    }

    public boolean isCafe_admob() {
        return isCafe_admob;
    }
    public void setCafe_admob(boolean cafe_admob) {
        isCafe_admob = cafe_admob;
    }

    public boolean isCafe_apbrain() {
        return isCafe_apbrain;
    }
    public void setCafe_apbrain(boolean cafe_apbrain) {
        isCafe_apbrain = cafe_apbrain;
    }

    public boolean isCafe_tapsell() {
        return isCafe_tapsell;
    }
    public void setCafe_tapsell(boolean cafe_tapsell) {
        isCafe_tapsell = cafe_tapsell;
    }

    public boolean isCafe_pandora() {
        return isCafe_pandora;
    }
    public void setCafe_pandora(boolean cafe_pandora) {
        isCafe_pandora = cafe_pandora;
    }

}
