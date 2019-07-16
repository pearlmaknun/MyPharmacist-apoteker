package io.pearlmaknun.mypharmacist_apoteker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status_konsultasi")
    @Expose
    private String statusKonsultasi;
    @SerializedName("data")
    @Expose
    private Profile data;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatusKonsultasi() {
        return statusKonsultasi;
    }

    public void setStatusKonsultasi(String statusKonsultasi) {
        this.statusKonsultasi = statusKonsultasi;
    }

    public Profile getData() {
        return data;
    }

    public void setData(Profile data) {
        this.data = data;
    }
}
