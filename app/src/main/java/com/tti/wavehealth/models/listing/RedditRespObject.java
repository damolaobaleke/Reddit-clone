package com.tti.wavehealth.models.listing;

public class RedditRespObject {
    String kind;
    Data data;

    public RedditRespObject(String kind, Data data) {
        this.kind = kind;
        this.data = data;
    }

    public String getKind() {
        return kind;
    }

    public Data getData() {
        return data;
    }
}
