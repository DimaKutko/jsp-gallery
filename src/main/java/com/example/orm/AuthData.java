package com.example.orm;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Date;

@JsonAutoDetect
public class AuthData {
    private String error;
    private String uid;
    private Long authMoment = (long) -1;

    public AuthData() {
    }

    public AuthData(String uid) {
        this.uid = uid;
        this.authMoment = new Date().getTime();
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public long getAuthMoment() {
        return authMoment;
    }

    public String getUid() {
        return uid;
    }

    public long momentOffset() {
        if (this.getAuthMoment() == -1) return -1;

        return new Date().getTime() - this.getAuthMoment();
    }

    public void updateAuthMoment() {
        this.authMoment = new Date().getTime();
    }

    public String toJson() {
        try {
            StringWriter writer = new StringWriter();
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(writer, this);

            return writer.toString();
        } catch (Exception ex) {
            System.out.println("AuthData toJson() error: " + ex.getMessage());
        }
        return null;
    }

    public static AuthData fromJson(String json) {
        if (json == null) return null;

        try {
            StringReader reader = new StringReader(json);
            ObjectMapper mapper = new ObjectMapper();

            return mapper.readValue(reader, AuthData.class);
        } catch (Exception ex) {
            System.out.println("AuthData fromJson() error: " + ex.getMessage());
        }
        return null;
    }
}

