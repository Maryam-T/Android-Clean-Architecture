package com.imanloo.photocollage.remoteplus.model;

import com.google.gson.annotations.SerializedName;

public class Dialog {
    @SerializedName("dlfc")
    private boolean isForce;

    @SerializedName("dltp")
    private String type;

    @SerializedName("dlt")
    private String title;

    @SerializedName("dld")
    private String description;

    @SerializedName("dliu")
    private String intent_uri;

    @SerializedName("dlib")
    private String intent_body;

    @SerializedName("dlbto")
    private String button_text_ok;

    @SerializedName("dlbtc")
    private String button_text_cancel;

    @SerializedName("dlsc")
    private int skip_count;

    public boolean isForce() {
        return isForce;
    }
    public void setForce(boolean force) {
        isForce = force;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getIntent_uri() {
        return intent_uri;
    }
    public void setIntent_uri(String intent_uri) {
        this.intent_uri = intent_uri;
    }

    public String getIntent_body() {
        return intent_body;
    }
    public void setIntent_body(String intent_body) {
        this.intent_body = intent_body;
    }

    public String getButton_text_ok() {
        return button_text_ok;
    }
    public void setButton_text_ok(String button_text_ok) {
        this.button_text_ok = button_text_ok;
    }

    public String getButton_text_cancel() {
        return button_text_cancel;
    }
    public void setButton_text_cancel(String button_text_cancel) {
        this.button_text_cancel = button_text_cancel;
    }

    public int getSkip_count() {
        return skip_count;
    }
    public void setSkip_count(int skip_count) {
        this.skip_count = skip_count;
    }

}
