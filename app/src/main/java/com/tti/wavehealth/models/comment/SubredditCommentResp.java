package com.tti.wavehealth.models.comment;

public class SubredditCommentResp {
    String kind;
    Data data;

    public SubredditCommentResp(String kind, Data data) {
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
