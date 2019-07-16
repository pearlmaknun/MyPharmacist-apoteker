package io.pearlmaknun.mypharmacist_apoteker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckActivityResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Check data;
    @SerializedName("user")
    @Expose
    private Konseli user;

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

    public Check getData() {
        return data;
    }

    public void setData(Check data) {
        this.data = data;
    }

    public Konseli getUser() {
        return user;
    }

    public void setUser(Konseli user) {
        this.user = user;
    }
}
