package com.tti.wavehealth.models.comment;

import java.util.HashMap;

public class CommentChildren {
    String kind;
    HashMap<String, Object> data;

    public CommentChildren(String kind, HashMap<String, Object> data) {
        this.kind = kind;
        this.data = data;
    }

    public String getKind() {
        return kind;
    }

    public HashMap<String, Object> getData() {
        return data;
    }
}
