package io.pearlmaknun.mypharmacist_apoteker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Profile {

    @SerializedName("apoteker_id")
    @Expose
    private String apotekerId;
    @SerializedName("apoteker_nik")
    @Expose
    private String apotekerNik;
    @SerializedName("apoteker_name")
    @Expose
    private String apotekerName;
    @SerializedName("apoteker_email")
    @Expose
    private String apotekerEmail;
    @SerializedName("apoteker_number")
    @Expose
    private String apotekerNumber;
    @SerializedName("apoteker_address")
    @Expose
    private String apotekerAddress;
    @SerializedName("apoteker_photo")
    @Expose
    private String apotekerPhoto;
    @SerializedName("apoteker_password")
    @Expose
    private String apotekerPassword;

    public String getApotekerId() {
        return apotekerId;
    }

    public void setApotekerId(String apotekerId) {
        this.apotekerId = apotekerId;
    }

    public String getApotekerNik() {
        return apotekerNik;
    }

    public void setApotekerNik(String apotekerNik) {
        this.apotekerNik = apotekerNik;
    }

    public String getApotekerName() {
        return apotekerName;
    }

    public void setApotekerName(String apotekerName) {
        this.apotekerName = apotekerName;
    }

    public String getApotekerEmail() {
        return apotekerEmail;
    }

    public void setApotekerEmail(String apotekerEmail) {
        this.apotekerEmail = apotekerEmail;
    }

    public String getApotekerNumber() {
        return apotekerNumber;
    }

    public void setApotekerNumber(String apotekerNumber) {
        this.apotekerNumber = apotekerNumber;
    }

    public String getApotekerAddress() {
        return apotekerAddress;
    }

    public void setApotekerAddress(String apotekerAddress) {
        this.apotekerAddress = apotekerAddress;
    }

    public String getApotekerPhoto() {
        return apotekerPhoto;
    }

    public void setApotekerPhoto(String apotekerPhoto) {
        this.apotekerPhoto = apotekerPhoto;
    }

    public String getApotekerPassword() {
        return apotekerPassword;
    }

    public void setApotekerPassword(String apotekerPassword) {
        this.apotekerPassword = apotekerPassword;
    }
}
