package io.pearlmaknun.mypharmacist_apoteker.model;

public class User {
    String id;
    String username;
    String main_id;

    public User(String id, String username) {
        this.id = id;
        this.username = username;
    }

    public String getMain_id() {
        return main_id;
    }

    public void setMain_id(String main_id) {
        this.main_id = main_id;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
