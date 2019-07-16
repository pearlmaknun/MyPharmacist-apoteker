package io.pearlmaknun.mypharmacist_apoteker.model;

public class Chat {

    String id_chat;
    String id_konsultasi;
    String pesan;
    String user_id;
    String apoteker_id;

    public Chat(String id_chat, String id_konsultasi, String pesan, String user_id, String apoteker_id) {
        this.id_chat = id_chat;
        this.id_konsultasi = id_konsultasi;
        this.pesan = pesan;
        this.user_id = user_id;
        this.apoteker_id = apoteker_id;
    }

    public Chat() {
    }

    public String getId_chat() {
        return id_chat;
    }

    public void setId_chat(String id_chat) {
        this.id_chat = id_chat;
    }

    public String getId_konsultasi() {
        return id_konsultasi;
    }

    public void setId_konsultasi(String id_konsultasi) {
        this.id_konsultasi = id_konsultasi;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getApoteker_id() {
        return apoteker_id;
    }

    public void setApoteker_id(String apoteker_id) {
        this.apoteker_id = apoteker_id;
    }
}
