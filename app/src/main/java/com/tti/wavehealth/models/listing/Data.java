package com.tti.wavehealth.models.listing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Data {
    String after;
    String modhash;
    int dist;
    Object geo_filter;
    List<Children> children;
    String before;

    public Data(String after, String before, String modhash, int dist, Object geo_filter, List<Children> children) {
        this.after = after;
        this.before = before;
        this.modhash = modhash;
        this.dist = dist;
        this.geo_filter = geo_filter;
        this.children = children;
    }

    public String getAfter() {
        return after;
    }

    public String getBefore() {
        return before;
    }

    public String getModhash() {
        return modhash;
    }

    public int getDist() {
        return dist;
    }

    public Object getGeo_filter() {
        return geo_filter;
    }

    public List<Children> getChildren() {
        return children;
    }
}
