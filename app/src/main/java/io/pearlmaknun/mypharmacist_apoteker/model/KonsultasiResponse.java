package io.pearlmaknun.mypharmacist_apoteker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KonsultasiResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("konsultasi")
    @Expose
    private Konsultasi konsultasi;

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

    public Konsultasi getKonsultasi() {
        return konsultasi;
    }

    public void setKonsultasi(Konsultasi konsultasi) {
        this.konsultasi = konsultasi;
    }
}
