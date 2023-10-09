package com.tti.wavehealth.models.comment;


import java.util.List;

public class Data {
    String after;
    String dist;
    String modhash;
    String geo_filter;
    List<CommentChildren> children;

    public Data(String after, String dist, String modhash, String geo_filter, List<CommentChildren> children) {
        this.after = after;
        this.dist = dist;
        this.modhash = modhash;
        this.geo_filter = geo_filter;
        this.children = children;
    }

    public String getAfter() {
        return after;
    }

    public String getDist() {
        return dist;
    }

    public String getModhash() {
        return modhash;
    }

    public String getGeo_filter() {
        return geo_filter;
    }

    public List<CommentChildren> getChildren() {
        return children;
    }
}
