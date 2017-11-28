package dk.sjd.demo.Model.Entities;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Reservation {

    private int id;
    private String name;
    private String phone;
    private int guest;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date date;
    private String request;

    public Reservation() {
    }

    public Reservation(int id, String name, String phone, int guest, Date date, String request) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.guest = guest;
        this.date = date;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }
}
