package io.pearlmaknun.mypharmacist_apoteker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class History {
    @SerializedName("chat_id")
    @Expose
    private String chatId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("apoteker_id")
    @Expose
    private String apotekerId;
    @SerializedName("waktu_pengajuan")
    @Expose
    private String waktuPengajuan;
    @SerializedName("start_chat")
    @Expose
    private String startChat;
    @SerializedName("update_chat")
    @Expose
    private String updateChat;
    @SerializedName("end_chat")
    @Expose
    private String endChat;
    @SerializedName("chat_duration")
    @Expose
    private String chatDuration;
    @SerializedName("status_chat")
    @Expose
    private String statusChat;
    @SerializedName("rating_star")
    @Expose
    private String ratingStar;
    @SerializedName("rating_comment")
    @Expose
    private String ratingComment;
    @SerializedName("rating_time")
    @Expose
    private String ratingTime;
    @SerializedName("pelapor")
    @Expose
    private String pelapor;
    @SerializedName("apoteker_stra")
    @Expose
    private String apotekerStra;
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
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("user_email")
    @Expose
    private String userEmail;
    @SerializedName("user_number")
    @Expose
    private String userNumber;
    @SerializedName("user_address")
    @Expose
    private String userAddress;
    @SerializedName("user_photo")
    @Expose
    private String userPhoto;
    @SerializedName("user_password")
    @Expose
    private String userPassword;

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
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

    public String getWaktuPengajuan() {
        return waktuPengajuan;
    }

    public void setWaktuPengajuan(String waktuPengajuan) {
        this.waktuPengajuan = waktuPengajuan;
    }

    public String getStartChat() {
        return startChat;
    }

    public void setStartChat(String startChat) {
        this.startChat = startChat;
    }

    public String getUpdateChat() {
        return updateChat;
    }

    public void setUpdateChat(String updateChat) {
        this.updateChat = updateChat;
    }

    public String getEndChat() {
        return endChat;
    }

    public void setEndChat(String endChat) {
        this.endChat = endChat;
    }

    public String getChatDuration() {
        return chatDuration;
    }

    public void setChatDuration(String chatDuration) {
        this.chatDuration = chatDuration;
    }

    public String getStatusChat() {
        return statusChat;
    }

    public void setStatusChat(String statusChat) {
        this.statusChat = statusChat;
    }

    public String getRatingStar() {
        return ratingStar;
    }

    public void setRatingStar(String ratingStar) {
        this.ratingStar = ratingStar;
    }

    public String getRatingComment() {
        return ratingComment;
    }

    public void setRatingComment(String ratingComment) {
        this.ratingComment = ratingComment;
    }

    public String getRatingTime() {
        return ratingTime;
    }

    public void setRatingTime(String ratingTime) {
        this.ratingTime = ratingTime;
    }

    public String getPelapor() {
        return pelapor;
    }

    public void setPelapor(String pelapor) {
        this.pelapor = pelapor;
    }

    public String getApotekerStra() {
        return apotekerStra;
    }

    public void setApotekerStra(String apotekerStra) {
        this.apotekerStra = apotekerStra;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
