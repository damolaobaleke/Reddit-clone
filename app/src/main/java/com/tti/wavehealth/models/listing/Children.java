package com.tti.wavehealth.models.listing;

import java.util.HashMap;
import java.util.List;

public class Children {
    String kind;
    HashMap<String, Object> data;

    public Children(String kind, HashMap<String, Object> data) {
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
