package ru.elerphore.data;

import org.bson.codecs.pojo.annotations.BsonProperty;

public class Pairs {
    @BsonProperty("changes")
    private Boolean changes = false;

    @BsonProperty("removed")
    private Boolean removed = false;

    @BsonProperty("error")
    private Boolean error = false;

    @BsonProperty("number")
    private Integer number;

    @BsonProperty("subgroup")
    private String subgroup;

    @BsonProperty("name")
    private String name;

    @BsonProperty("teacher")
    private String teacher;

    @BsonProperty("classroom")
    private String classroom;

    @Override
    public String toString() {
        var string = new StringBuilder()
                .append("№: ").append(this.number)
                .append(" Группа:").append(this.subgroup).append(" Предмет: ").append(this.name).append(" КБ: ").append(this.classroom).append(" Препод: ").append(this.teacher)
                .append("\n\n");

        return string.toString();
    }

    public Boolean getChanges() {
        return changes;
    }

    public void setChanges(Boolean changes) {
        this.changes = changes;
    }

    public Boolean getRemoved() {
        return removed;
    }

    public void setRemoved(Boolean removed) {
        this.removed = removed;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getNumber() {
        return this.number;
    }

    public String getSubgroup() {
        return this.subgroup;
    }
    public void setSubgroup(String subgroup) {
        this.subgroup = subgroup;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getTeacher() {
        return this.teacher;
    }
    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getClassroom() {
        return this.classroom;
    }
    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }
}
