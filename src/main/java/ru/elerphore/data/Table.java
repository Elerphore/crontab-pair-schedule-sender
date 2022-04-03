package ru.elerphore.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.BasicDBObject;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Table {
    @JsonProperty("displayName")
    private String displayName;

    @JsonProperty("group")
    private ArrayList<String> group;

    @JsonProperty("date")
    private LocalDateTime date;

    @JsonProperty("pairs")
    private ArrayList<Pairs> pairs;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public ArrayList<String> getGroup() {
        return group;
    }

    public void setGroup(ArrayList<String> group) {
        this.group = group;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public ArrayList<Pairs> getPairs() {
        return pairs;
    }

    public void setPairs(ArrayList<Pairs> pairs) {
        this.pairs = pairs;
    }
}
