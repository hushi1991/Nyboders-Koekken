package dk.sjd.demo.Model.Entities;

public class Employee {

    private int id;
    private String name;
    private double totalHours;

    public Employee() {
    }

    public Employee(int id, String name, double totalHours) {
        this.id = id;
        this.name = name;
        this.totalHours = totalHours;
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

    public double getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(double totalHours) {
        this.totalHours = totalHours;
    }
}