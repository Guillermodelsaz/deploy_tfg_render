package com.example.demo;

public class RequestBodyDTO {
    private String torneoId;
    private RegaloFile regalosFile;

    public RequestBodyDTO() {}

    public String getTorneoId() {
        return torneoId;
    }

    public void setTorneoId(String torneoId) {
        this.torneoId = torneoId;
    }

    public RegaloFile getRegalosFile() {
        return regalosFile;
    }

    public void setRegalosFile(RegaloFile regalosFile) {
        this.regalosFile = regalosFile;
    }
}

