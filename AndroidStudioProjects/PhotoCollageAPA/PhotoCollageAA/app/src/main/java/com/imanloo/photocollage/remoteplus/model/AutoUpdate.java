package com.imanloo.photocollage.remoteplus.model;

import com.google.gson.annotations.SerializedName;

public class AutoUpdate {
    @SerializedName("tp")
    private String type;

    @SerializedName("lv")
    private String last_playstore_version;

    @SerializedName("dt")
    private String dialog_title;

    @SerializedName("dd")
    private String dialog_description;

    @SerializedName("bo")
    private String button_ok_text;

    @SerializedName("bc")
    private String button_cancel_text;

    @SerializedName("auf")
    private boolean isForce;

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getLast_playstore_version() {
        return last_playstore_version;
    }
    public void setLast_playstore_version(String last_playstore_version) {
        this.last_playstore_version = last_playstore_version;
    }

    public String getDialog_title() {
        return dialog_title;
    }
    public void setDialog_title(String dialog_title) {
        this.dialog_title = dialog_title;
    }

    public String getButton_ok_text() {
        return button_ok_text;
    }
    public void setButton_ok_text(String button_ok_text) {
        this.button_ok_text = button_ok_text;
    }

    public String getButton_cancel_text() {
        return button_cancel_text;
    }
    public void setButton_cancel_text(String button_cancel_text) {
        this.button_cancel_text = button_cancel_text;
    }

    public String getDialog_description() {
        return dialog_description;
    }
    public void setDialog_description(String dialog_description) {
        this.dialog_description = dialog_description;
    }

    public boolean isForce() {
        return isForce;
    }
    public void setForce(boolean force) {
        isForce = force;
    }

}
