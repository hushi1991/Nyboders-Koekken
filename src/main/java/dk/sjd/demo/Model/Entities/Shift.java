package dk.sjd.demo.Model.Entities;

import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;

public class Shift {

    private int id;
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private Time shiftStart;
    private Time shiftEnd;
    private int hours;


    public Shift() {
    }

    public Shift(int id, String name, LocalDate date, Time shiftStart, Time shiftEnd, int hours) {
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
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
