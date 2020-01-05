package io.pearlmaknun.mypharmacist_apoteker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Apotek {

    @SerializedName("apotik_id")
    @Expose
    private String apotikId;
    @SerializedName("apotik_name")
    @Expose
    private String apotikName;

    public Apotek(String apotikId, String apotikName) {
        this.apotikId = apotikId;
        this.apotikName = apotikName;
    }

    public String getApotikId() {
        return apotikId;
    }

    public void setApotikId(String apotikId) {
        this.apotikId = apotikId;
    }

    public String getApotikName() {
        return apotikName;
    }

    public void setApotikName(String apotikName) {
        this.apotikName = apotikName;
    }

}
