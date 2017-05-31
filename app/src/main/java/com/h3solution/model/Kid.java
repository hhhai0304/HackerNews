package com.h3solution.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * The ids of the item's comments, in ranked display order.
 * Created by HHHai on 29-05-2017.
 */
public class Kid {
    @SerializedName("by")
    private String by;
    @SerializedName("id")
    private int id;
    @SerializedName("kids")
    private List<Kid> kids;
    @SerializedName("parent")
    private int parent;
    @SerializedName("text")
    private String text;
    @SerializedName("time")
    private int time;
    @SerializedName("type")
    private String type;

    public Kid() {
    }

    public Kid(String by, int id, List<Kid> kids, int parent, String text, int time, String type) {
        this.by = by;
        this.id = id;
        this.kids = kids;
        this.parent = parent;
        this.text = text;
        this.time = time;
        this.type = type;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Kid> getKids() {
        return kids;
    }

    public void setKids(List<Kid> kids) {
        this.kids = kids;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}