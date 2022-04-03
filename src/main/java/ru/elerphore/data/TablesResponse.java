package ru.elerphore.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TablesResponse {
    @JsonProperty("items")
    public ArrayList<Table> items;

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for(Table table : this.items) {
            String pairs = "";
                    for(Pairs pair : table.getPairs()) {
                        pairs = new StringBuilder(pairs).append(pair).toString();
                    }
            str.append(table.getDisplayName()).append("\n")
                    .append(table.getDate().format(DateTimeFormatter.ISO_DATE_TIME)).append(" ").append(table.getDate().getDayOfWeek()).append("\n")
                    .append(pairs).append("\n");
        }
        return str.toString();
    }
}
