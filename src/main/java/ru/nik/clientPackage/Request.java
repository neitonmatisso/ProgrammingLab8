package ru.nik.clientPackage;

import java.io.Serializable;

public class Request implements Serializable {
    private String commmandName;
    private String options;
    private String owner;
    private RequestType requestType;
    private String token;

    public Request(String commmandName, String options,String owner, RequestType requestType, String token) {
        this.commmandName = commmandName;
        this.options = options;
        this.owner = owner;
        this.requestType = requestType;
        this.token  = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public String getCommmandName() {
        return commmandName;
    }

    public void setCommmandName(String commmandName) {
        this.commmandName = commmandName;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Request{" +
                "commmandName='" + commmandName + '\'' +
                ", options='" + options + '\'' +
                '}';
    }
}
