package ru.elerphore.data;

import org.bson.types.ObjectId;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;

public class Day {
    private ObjectId id;

    private DayOfWeek dayName;

    private ArrayList<Pairs> pairs;

    public Day() {}

    public Day(DayOfWeek dayName, ArrayList<Pairs> pairs) {
        this.dayName = dayName;
        this.pairs = pairs;

    }

    public DayOfWeek getDayName() {
        return dayName;
    }

    public void setDayName(DayOfWeek dayName) {
        this.dayName = dayName;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ArrayList<Pairs> getPairs() {
        return pairs;
    }

    public void setPairs(ArrayList<Pairs> pairs) {
        this.pairs = pairs;
    }

    @Override
    public String toString() {
        String pairs = "";
        this.pairs.sort(Comparator.comparing(Pairs::getNumber));

        for(Pairs pair : this.pairs) {
            if(pair.getSubgroup().equals("common") || pair.getSubgroup().equals("second"))
                pairs = new StringBuilder(pairs).append(pair).toString();
        }

        var string = new StringBuilder()
                .append(this.dayName).append("\n")
                .append(pairs);

        return string.toString();
    }
}
