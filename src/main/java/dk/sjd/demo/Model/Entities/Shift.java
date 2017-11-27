package dk.sjd.demo.Model.Entities;

import java.sql.Time;
import java.util.Date;

public class Shift {

    private int id;
    private String name;
    private Date date;
    private Time shiftStart;
    private Time shiftEnd;
    private int hours;

    public Shift() {
    }

    public Shift(int id, String name, Date date, Time shiftStart, Time shiftEnd, int hours) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.shiftStart = shiftStart;
        this.shiftEnd = shiftEnd;
        this.hours = hours;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getShiftStart() {
        return shiftStart;
    }

    public void setShiftStart(Time shiftStart) {
        this.shiftStart = shiftStart;
    }

    public Time getShiftEnd() {
        return shiftEnd;
    }

    public void setShiftEnd(Time shiftEnd) {
        this.shiftEnd = shiftEnd;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }
}
