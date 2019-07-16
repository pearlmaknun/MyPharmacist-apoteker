package io.pearlmaknun.mypharmacist_apoteker.data;

public class Constan {

    //public static final String BASE_URL = "http://localhost:8080";
    public static final String BASE_URL = "https://anaksulung.id/public";
    public static final String BASE_URL_USER = BASE_URL + "/apoteker";

    //auth
    public static final String REGISTER = BASE_URL + "/register_apoteker";
    public static final String LOGIN = BASE_URL + "/login_apoteker";
    public static final String LOGOUT = BASE_URL_USER + "/logout";

    //profile and others information
    public static final String PROFIL = BASE_URL_USER + "/profile";
    public static final String STATUS_KONSULTASI = BASE_URL_USER + "/statuskonsultasi/";
    public static final String LOKASI_UPDATE = BASE_URL_USER + "/lokasiapoteker/";

    // konsultasi
    public static final String CHECK = BASE_URL_USER + "/statuscon";
    public static final String ACCEPT_REQUEST = BASE_URL_USER + "/profile";
}