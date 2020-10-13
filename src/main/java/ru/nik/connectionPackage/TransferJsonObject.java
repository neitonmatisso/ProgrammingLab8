package ru.nik.connectionPackage;

import java.io.Serializable;

public class TransferJsonObject implements Serializable {
    private String transferJson;
    public TransferJsonObject(String json){
        transferJson = json;
    }
    public TransferJsonObject(){

    }
    public void setTransferJson(String transferJson) {
        this.transferJson = transferJson;
    }

    public String getTransferJson() {
        return transferJson;
    }
}
