package com.fajarnandagusti.sisimangi.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Gustiawan on 2/2/2019.
 */

public class ResponseData {

    @SerializedName("success")
    String success;
    @SerializedName("message")
    String message;
    @SerializedName("username")
    String username;

    @SerializedName("status")
    String status;

    @SerializedName("nip")
    String nip;

    @SerializedName("nik")
    String nik;


    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }


    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ResponseData(String success,  String username,String message, String nip, String status) {
        this.success = success;
        this.username = username;
        this.message = message;
        this.nip = nip;
        this.status = status;
    }


}
