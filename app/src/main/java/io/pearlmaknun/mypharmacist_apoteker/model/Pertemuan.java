package io.pearlmaknun.mypharmacist_apoteker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Pertemuan implements Serializable {

    @SerializedName("pertemuan_id")
    @Expose
    private String pertemuanId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("apoteker_id")
    @Expose
    private String apotekerId;
    @SerializedName("konsultasi_id")
    @Expose
    private String konsultasiId;
    @SerializedName("pertemuan_waktu")
    @Expose
    private String pertemuanWaktu;
    @SerializedName("pertemuan_alamat")
    @Expose
    private String pertemuanAlamat;
    @SerializedName("pertemuan_lokasi")
    @Expose
    private String pertemuanLokasi;
    @SerializedName("pertemuan_detail")
    @Expose
    private String pertemuanDetail;
    @SerializedName("pertemuan_status")
    @Expose
    private String pertemuanStatus;
    @SerializedName("apoteker_name")
    @Expose
    private String apotekerName;
    @SerializedName("user_name")
    @Expose
    private String userName;

    public String getPertemuanId() {
        return pertemuanId;
    }

    public void setPertemuanId(String pertemuanId) {
        this.pertemuanId = pertemuanId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getApotekerId() {
        return apotekerId;
    }

    public void setApotekerId(String apotekerId) {
        this.apotekerId = apotekerId;
    }

    public String getKonsultasiId() {
        return konsultasiId;
    }

    public void setKonsultasiId(String konsultasiId) {
        this.konsultasiId = konsultasiId;
    }

    public String getPertemuanWaktu() {
        return pertemuanWaktu;
    }

    public void setPertemuanWaktu(String pertemuanWaktu) {
        this.pertemuanWaktu = pertemuanWaktu;
    }

    public String getPertemuanAlamat() {
        return pertemuanAlamat;
    }

    public void setPertemuanAlamat(String pertemuanAlamat) {
        this.pertemuanAlamat = pertemuanAlamat;
    }

    public String getPertemuanLokasi() {
        return pertemuanLokasi;
    }

    public void setPertemuanLokasi(String pertemuanLokasi) {
        this.pertemuanLokasi = pertemuanLokasi;
    }

    public String getPertemuanDetail() {
        return pertemuanDetail;
    }

    public void setPertemuanDetail(String pertemuanDetail) {
        this.pertemuanDetail = pertemuanDetail;
    }

    public String getPertemuanStatus() {
        return pertemuanStatus;
    }

    public void setPertemuanStatus(String pertemuanStatus) {
        this.pertemuanStatus = pertemuanStatus;
    }

    public String getApotekerName() {
        return apotekerName;
    }

    public void setApotekerName(String apotekerName) {
        this.apotekerName = apotekerName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
