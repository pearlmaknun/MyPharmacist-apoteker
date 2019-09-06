package io.pearlmaknun.mypharmacist_apoteker.model;

public class Chat {

    String id_chat;
    String id_konsultasi;
    String pesan;
    String pengirim;
    String penerima;

    public Chat(String id_chat, String id_konsultasi, String pesan, String pengirim, String penerima) {
        this.id_chat = id_chat;
        this.id_konsultasi = id_konsultasi;
        this.pesan = pesan;
        this.pengirim = pengirim;
        this.penerima = penerima;
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

    public String getPengirim() {
        return pengirim;
    }

    public void setPengirim(String pengirim) {
        this.pengirim = pengirim;
    }

    public String getPenerima() {
        return penerima;
    }

    public void setPenerima(String penerima) {
        this.penerima = penerima;
    }
}
