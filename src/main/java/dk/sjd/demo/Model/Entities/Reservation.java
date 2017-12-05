package dk.sjd.demo.Model.Entities;

import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;

public class Reservation {

    private int id;
    private String name;
    private String phone;
    private int guest;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private String time;
    private String request;

    public Reservation() {
    }

    public Reservation(int id, String name, String phone, int guest, LocalDate date, String time, String request) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.guest = guest;
        this.date = date;
        this.time = time;
        this.request = request;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getGuest() {
        return guest;
    }

    public void setGuest(int guest) {
        this.guest = guest;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }
}
